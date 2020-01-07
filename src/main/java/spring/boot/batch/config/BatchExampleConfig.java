package spring.boot.batch.config;

import java.util.UUID;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.boot.batch.constants.AppConstants;
import spring.boot.batch.job.JobFactory;
import spring.boot.batch.model.Product;
import spring.boot.batch.processor.IdentityProcessor;
import spring.boot.batch.reader.CsvReader;
import spring.boot.batch.reader.JdbcReader;
import spring.boot.batch.step.StepFactory;
import spring.boot.batch.writer.JdbcWriter;

@Configuration
public class BatchExampleConfig {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    JdbcReader jdbcReader;

    @Autowired
    JdbcWriter jdbcWriter;

    //@Autowired
    //@Qualifier("h2DataSource")
    //DataSource h2DataSource;

    @Autowired
    DataSource dataSource;

    @Autowired
    @Qualifier("identityProcessor")
    ItemProcessor<Product, Product> identityProcessor;

    @Autowired
    @Qualifier("blankAddressProcessor")
    ItemProcessor<Product, Product> blankAddressProcessor;

    @Autowired
    StepFactory stepFactory;

    @Autowired
    IdentityProcessor processor;

    @Autowired
    JobFactory jobFactory;

    @Bean
    public FlatFileItemReader<Product> readerCsv() {
        return new CsvReader("import.csv");
    }

    @Bean
    public JdbcCursorItemReader<Product> readerDB() {
        return new JdbcReader(dataSource);
    }

    @Bean
    public JdbcBatchItemWriter<Product> writerDB() {
        return new JdbcWriter(dataSource);
    }

    @Bean
    public Step stepCsvToDb() {
        /*
        return stepBuilderFactory.get(AppConstants.STEP_CSV_TO_DB)
                .<Product, Product>chunk(100)
                .reader(new CsvReader("import.csv")) //
                .processor(identityProcessor)
                .writer(jdbcWriter) //WTF ??? new JdbcWriter(dataSource)
                .build();
                */
        /*
        return stepBuilderFactory.get(AppConstants.STEP_CSV_TO_DB)
                .<Product, Product>chunk(100)
                .reader(readerCsv())
                .processor(identityProcessor)
                .writer(writerDB())
                .build();
*/
      return stepFactory.createCsvToDbStep("import.csv");
    }

    @Bean
    public Step stepDbToCsv() {
        /*
        return stepBuilderFactory.get(AppConstants.STEP_DB_TO_CSV)
                .<Product, Product>chunk(100)
                .reader(readerDB())
                .processor(blankAddressProcessor)
                .writer(new CsvWriter("products-export.csv"))
                .build();
                */
        return stepFactory.createDbToCsvStep("products-export.csv");
    }

    @Bean
    public Job mainJob() {
        /*
        return jobBuilderFactory.get("JOB-" + UUID.randomUUID().toString())
                .incrementer(new RunIdIncrementer())
                .flow(stepCsvToDb())
                .on(AppConstants.STEP_COMPLETED).to(stepDbToCsv())
                .on(AppConstants.STEP_FAILED).fail()
                .end()
                .build();
                */
        return jobFactory.createComplexJob("COMPLEX_JOB", "import.csv", "products-export.csv");
    }

}
