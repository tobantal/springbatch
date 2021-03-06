package spring.boot.batch.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.boot.batch.constants.AppConstants;
import spring.boot.batch.model.KoListMemberView;

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
    public Step dbToCsvStep(
            @Qualifier("jdbcReader") ItemReader<KoListMemberView> jdbcReader,
            @Qualifier("blankAddressProcessor") ItemProcessor<KoListMemberView, KoListMemberView> blankAddressProcessor,
            @Qualifier("csvWriter") ItemWriter<KoListMemberView> csvWriter,
        @Qualifier("writeListener") StepExecutionListenerSupport listener) {
		return stepBuilderFactory.get(AppConstants.STEP_DB_TO_CSV)
                .<KoListMemberView, KoListMemberView>chunk(AppConstants.STEP_CHUNK)
                .reader(jdbcReader)
                .processor(blankAddressProcessor)
                .writer(csvWriter)
                .listener(listener)
                .build();
        }

    /*
     * @Bean public Step csvToDbStep(
     * 
     * @Qualifier("csvReader") ItemReader<Product> csvReader,
     * 
     * @Qualifier("identityProcessor") ItemProcessor<Product, Product>
     * identityProcessor,
     * 
     * @Qualifier("jdbcWriter") ItemWriter<Product> jdbcWriter) { return
     * stepBuilderFactory.get(AppConstants.STEP_CSV_TO_DB) .<Product,
     * Product>chunk(AppConstants.STEP_CHUNK) .reader(csvReader)
     * .processor(identityProcessor) //TODO add listeners .writer(jdbcWriter) //new
     * JdbcBatchItemWriterWrapper() .build(); }
     */

}