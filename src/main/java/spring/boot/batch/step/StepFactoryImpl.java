package spring.boot.batch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import spring.boot.batch.constants.AppConstants;
import spring.boot.batch.model.Product;
import spring.boot.batch.processor.BlankAddressProcessor;
import spring.boot.batch.processor.IdentityProcessor;
import spring.boot.batch.reader.CsvReader;
import spring.boot.batch.reader.JdbcReader;
import spring.boot.batch.wrapper.JdbcBatchItemWriterWrapper;
import spring.boot.batch.writer.CsvWriter;
import spring.boot.batch.writer.JdbcWriter;

@Component
@RequiredArgsConstructor
public class StepFactoryImpl implements StepFactory {

    private final StepBuilderFactory stepBuilderFactory;
    private final IdentityProcessor processor;
    private final JdbcWriter jdbcWriter;
    private final JdbcReader jdbcReader;
    private final BlankAddressProcessor blankAddressProcessor;
    private final CsvReader csvReader;
    private final CsvWriter csvWriter;

	@Override
	public Step createCsvToDbStep() {
		return stepBuilderFactory.get(AppConstants.STEP_CSV_TO_DB)
                .<Product, Product>chunk(AppConstants.STEP_CHUNK)
                .reader(csvReader)
                .processor(processor)
                //TODO add listener
                .writer(new JdbcBatchItemWriterWrapper(jdbcWriter, 4))
                .build();
	}

	@Override
	public Step createDbToCsvStep() {
        jdbcReader.setSql("SELECT * FROM products where product_id >= 2 and product_id <= 7");
		return stepBuilderFactory.get(AppConstants.STEP_DB_TO_CSV)
                .<Product, Product>chunk(AppConstants.STEP_CHUNK)
                .reader(jdbcReader)
                .processor(blankAddressProcessor)
                .writer(csvWriter)
                .build();
	}

}