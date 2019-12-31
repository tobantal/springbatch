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
import org.springframework.core.io.ClassPathResource;

import spring.boot.batch.ProductPagingAndSortingRepository;
import spring.boot.batch.model.Product;
import spring.boot.batch.util.StringHeaderWriter;

//@Configuration
//@EnableBatchProcessing
public class BatchConfiguration {

    // @Autowired
    public JobBuilderFactory jobBuilderFactory;

    // @Autowired
    public StepBuilderFactory stepBuilderFactory;

    // @Autowired
    public ProductPagingAndSortingRepository productPagingAndSortingRepository;

    // @Bean
    public ItemWriter<Product> writer() {
        FlatFileItemWriter<Product> writer = new FlatFileItemWriter<>();

        String exportFileHeader = "Name,Desc,Price";
        StringHeaderWriter headerWriter = new StringHeaderWriter(exportFileHeader);
        writer.setHeaderCallback(headerWriter);
        writer.setResource(new ClassPathResource("D:/sample-data.csv"));

        LineAggregator<Product> lineAgg = createCustomerInsrAgg();
        writer.setLineAggregator(lineAgg);

        return writer;
    }

    // @Bean
    public LineAggregator<Product> createCustomerInsrAgg() {

        DelimitedLineAggregator<Product> deliAgg = new DelimitedLineAggregator<>();
        deliAgg.setDelimiter(",");

        FieldExtractor<Product> fieldExtractor = createCustomerInsrExtractor();
        deliAgg.setFieldExtractor(fieldExtractor);
        return deliAgg;

    }

    // @Bean
    public FieldExtractor<Product> createCustomerInsrExtractor() {
        BeanWrapperFieldExtractor<Product> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[] { "name", "description", "price" });
        return fieldExtractor;
    }

    // @Bean
    public ItemReader<Product> reader() {
        RepositoryItemReader<Product> reader = new RepositoryItemReader<>();
        reader.setRepository(productPagingAndSortingRepository);
        reader.setMethodName("findAll");
        return reader;
    }

    // @Bean
    public Job job() {
        return jobBuilderFactory.get("job2").incrementer(new RunIdIncrementer()).flow(step()).end().build();
    }

    // @Bean
    public Step step() {
        return stepBuilderFactory.get("step2").<Product, Product>chunk(10).reader(reader()).writer(writer())
                .build();
    }

}