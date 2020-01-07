package spring.boot.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import spring.boot.batch.constants.AppConstants;
import spring.boot.batch.model.Product;
import spring.boot.batch.processor.BlankAddressProcessor;
import spring.boot.batch.processor.IdentityProcessor;
import spring.boot.batch.reader.CsvReader;
import spring.boot.batch.reader.JdbcReader;
import spring.boot.batch.repository.ProductPagingAndSortingRepository;
import spring.boot.batch.util.StringHeaderWriter;
import spring.boot.batch.writer.CsvWriter;
import spring.boot.batch.writer.JdbcWriter;

@Configuration
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private CsvReader csvReader;

    @Autowired
    private IdentityProcessor identityProcessor;

    @Autowired
    private JdbcWriter jdbcWriter;

    @Autowired
    private JdbcReader jdbcReader;

    @Autowired
    private BlankAddressProcessor blankAddressProcessor;

    @Autowired
    private CsvWriter csvWriter;

    // @Autowired
    public ProductPagingAndSortingRepository productPagingAndSortingRepository;

    // @Bean
    public ItemReader<Product> reader() {
        RepositoryItemReader<Product> reader = new RepositoryItemReader<>();
        reader.setRepository(productPagingAndSortingRepository);
        reader.setMethodName("findAll");
        return reader;
    }

    @Bean
    public Step dbToCsvStep() {
		return stepBuilderFactory.get(AppConstants.STEP_DB_TO_CSV)
                .<Product, Product>chunk(AppConstants.STEP_CHUNK)
                .reader(jdbcReader)
                .processor(blankAddressProcessor)
                .writer(csvWriter)
                .build();
        }

    @Bean
    public Step csvToDbStep() {
		return stepBuilderFactory.get(AppConstants.STEP_CSV_TO_DB)
                .<Product, Product>chunk(AppConstants.STEP_CHUNK)
                .reader(csvReader)
                .processor(identityProcessor)
                //TODO add listener
                .writer(jdbcWriter) //new JdbcBatchItemWriterWrapper(
                .build();
    }

    @Bean
    public Job complexJob() { //String jobName
        return jobBuilderFactory.get("complex_job")
                .incrementer(new RunIdIncrementer())
                .flow(csvToDbStep())
                .on(AppConstants.STEP_COMPLETED).to(dbToCsvStep())
                .on(AppConstants.STEP_FAILED).fail()
                .end()
                .build();
    }
}