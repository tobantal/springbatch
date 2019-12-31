package spring.boot.batch.config;

import java.sql.ResultSet;
import java.sql.SQLException;

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

import spring.boot.batch.model.Product;
import spring.boot.batch.util.StringHeaderWriter;

@Configuration
public class BatchExampleConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean
    public FlatFileItemReader<Product> reader1() {
        FlatFileItemReader<Product> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("import.csv"));
        reader.setLineMapper(new DefaultLineMapper<Product>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"id", "name", "description", "price"});
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
    public JdbcCursorItemReader<Product> reader() {
        JdbcCursorItemReader<Product> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT * FROM products");
        reader.setRowMapper(new UserRowMapper());
        return reader;
    }

    @Bean
    public ItemProcessor<Product, Product> processor() {
        return person -> person;
    }

    // @Bean
    public JdbcBatchItemWriter<Product> writer() {
        JdbcBatchItemWriter<Product> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO products2 " +
                "(product_id, name, description, price) " +
                "VALUES (:id, :name, :description, :price)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Product, Product>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(databaseCsvItemWriter())
                .build();
    }

    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importProductsJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    ItemWriter<Product> databaseCsvItemWriter() {
        FlatFileItemWriter<Product> csvFileWriter = new FlatFileItemWriter<>();

        String exportFileHeader = "NAME;EMAIL_ADDRESS;PACKAGE";
        StringHeaderWriter headerWriter = new StringHeaderWriter(exportFileHeader);
        csvFileWriter.setHeaderCallback(headerWriter);

        String exportFilePath = "/tmp/students.csv";
        csvFileWriter.setResource(new FileSystemResource(exportFilePath));

        LineAggregator<Product> lineAggregator = createStudentLineAggregator();
        csvFileWriter.setLineAggregator(lineAggregator);

        return csvFileWriter;
    }

    private LineAggregator<Product> createStudentLineAggregator() {
        DelimitedLineAggregator<Product> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");

        FieldExtractor<Product> fieldExtractor = createStudentFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);

        return lineAggregator;
    }

    private FieldExtractor<Product> createStudentFieldExtractor() {
        BeanWrapperFieldExtractor<Product> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] { "name", "description", "price" });
        return extractor;
    }

}

class UserRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet res, int rowNum) throws SQLException {
        Product p = new Product(res.getLong("id"), res.getString("name"), res.getString("description"),
                res.getDouble("price"));
        System.out.println("################## Product ################### :" + p.getName());
        return p;
    }

}
