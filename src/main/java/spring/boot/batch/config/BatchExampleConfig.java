package spring.boot.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.boot.batch.model.Product;
import spring.boot.batch.reader.CsvReader;
import spring.boot.batch.reader.JdbcReader;
import spring.boot.batch.writer.CsvWriter;
import spring.boot.batch.writer.JdbcWriter;

@Configuration
public class BatchExampleConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    //@Autowired
    //public DataSource dataSource;

    @Autowired public JdbcReader jdbcReader;

    @Bean
    public FlatFileItemReader<Product> readerCsv() {
        return new CsvReader("import.csv");
    }

    @Bean
    public JdbcCursorItemReader<Product> readerDB(DataSource dataSource) {
        return new JdbcReader(dataSource);
    }

    @Bean
    public ItemProcessor<Product, Product> identityProcessor() {
        return person -> person;
    }

    @Bean
    public JdbcBatchItemWriter<Product> writerDB(DataSource dataSource) {
        return new JdbcWriter(dataSource);
    }

    @Bean
    public Step step1(DataSource dataSource) {
        return stepBuilderFactory.get("step1")
                .<Product, Product>chunk(100)
                .reader(readerCsv())
                .processor(identityProcessor())
                .writer(writerDB(dataSource))
                .build();
    }

    @Bean
    public Step step2(DataSource dataSource) {
        return stepBuilderFactory.get("step2")
                .<Product, Product>chunk(10)
                .reader(readerDB(dataSource))
                .processor(identityProcessor())
                .writer(new CsvWriter("products-export.csv"))
                .build();
    }

    @Bean
    public Job mainJob(DataSource dataSource) {
        return jobBuilderFactory.get("MAIN_JOB") // AppConstants.JOB_NAME_DEFERRAL
                .incrementer(new RunIdIncrementer())
		.flow(step1(dataSource))
		.on("COMPLETED").to(step2(dataSource))
		.on("FAILED").fail()
                .end()
                .build();
    }

}
