package spring.boot.batch.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;

import spring.boot.batch.mapper.ProductRowMapper;
import spring.boot.batch.model.Product;
import spring.boot.batch.reader.JdbcReader;
import spring.boot.batch.util.StringHeaderWriter;
import spring.boot.batch.wrapper.JdbcBatchItemWriterWrapper;
import spring.boot.batch.writer.CsvWriter;

@Configuration
public class BatchExampleConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    //@Autowired
    //public DataSource dataSource;

    @Autowired public JdbcReader jdbcReader;

    @Autowired public CsvWriter csvWriter;

    @Bean
    public FlatFileItemReader<Product> readerCsv() {
        FlatFileItemReader<Product> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("import.csv"));
        reader.setLineMapper(new DefaultLineMapper<Product>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"id", "name", "description", "price"});
                setDelimiter(";");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Product>() {{
                setTargetType(Product.class);
            }});
        }});
        return reader;
    }

    /*
     * @Bean public ItemReader<Product> reader() { RepositoryItemReader<Product>
     * reader = new RepositoryItemReader<>();
     * reader.setRepository(productPagingAndSortingRepository);
     * reader.setMethodName("findAll"); return reader; }
     */

    @Bean
    public JdbcCursorItemReader<Product> readerDB(DataSource dataSource) {
        return jdbcReader;
    }

    @Bean
    public ItemProcessor<Product, Product> identityProcessor() {
        return person -> person;
    }

    @Bean
    public JdbcBatchItemWriter<Product> writerDB(DataSource dataSource) {
        JdbcBatchItemWriter<Product> writer = new JdbcBatchItemWriterWrapper();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO products " +
                "(product_id, name, description, price) " +
                "VALUES (:id, :name, :description, :price)");
        writer.setDataSource(dataSource);
        return writer;
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
                .writer(csvWriter.file("products-export.csv"))
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
