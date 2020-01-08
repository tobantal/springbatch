package spring.boot.batch.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.boot.batch.constants.AppConstants;
import spring.boot.batch.model.Product;
import spring.boot.batch.processor.BlankAddressProcessor;
import spring.boot.batch.processor.IdentityProcessor;
import spring.boot.batch.reader.CsvReader;
import spring.boot.batch.reader.JdbcReader;
import spring.boot.batch.writer.CsvWriter;
import spring.boot.batch.writer.JdbcWriter;

@Configuration
public class BatchConfiguration { // rename to StepsConfig

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    /*
    // @Autowired
    public ProductPagingAndSortingRepository productPagingAndSortingRepository;

    // @Bean
    public ItemReader<Product> reader() {
        RepositoryItemReader<Product> reader = new RepositoryItemReader<>();
        reader.setRepository(productPagingAndSortingRepository);
        reader.setMethodName("findAll");
        return reader;
    }
    */

    @Bean
    public Step dbToCsvStep(JdbcReader jdbcReader,
        @Qualifier("blankAddressProcessor") ItemProcessor<Product, Product> blankAddressProcessor,
        CsvWriter csvWriter) {
		return stepBuilderFactory.get(AppConstants.STEP_DB_TO_CSV)
                .<Product, Product>chunk(AppConstants.STEP_CHUNK)
                .reader(jdbcReader)
                .processor(blankAddressProcessor)
                .writer(csvWriter)
                //TODO add listener
                .build();
        }

    @Bean
    public Step csvToDbStep(CsvReader csvReader,
        @Qualifier("identityProcessor") ItemProcessor<Product, Product> identityProcessor,
        JdbcWriter jdbcWriter) {
		return stepBuilderFactory.get(AppConstants.STEP_CSV_TO_DB)
                .<Product, Product>chunk(AppConstants.STEP_CHUNK)
                .reader(csvReader)
                .processor(identityProcessor)
                //TODO add listener
                .writer(jdbcWriter) //new JdbcBatchItemWriterWrapper(
                .build();
    }

}