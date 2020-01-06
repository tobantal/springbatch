package spring.boot.batch.step;


import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import spring.boot.batch.constants.AppConstants;
import spring.boot.batch.model.Product;
import spring.boot.batch.reader.CsvReader;
import spring.boot.batch.writer.JdbcWriter;

@Component
@RequiredArgsConstructor
public class StepFactoryImpl implements StepFactory {

    private final StepBuilderFactory stepBuilderFactory;

    @Qualifier("identityProcessor")
    private final ItemProcessor<Product, Product> identityProcessor;

    @Qualifier("h2DataSource")
    private final DataSource h2DataSource;

	@Override
	public Step createCsvToDbStep(String importFile) {
		return stepBuilderFactory.get(AppConstants.STEP_CSV_TO_DB)
                .<Product, Product>chunk(100)
                .reader(new CsvReader(importFile))
                .processor(identityProcessor)
                .writer(new JdbcWriter(h2DataSource))
                .build();
	}

	@Override
	public Step createDbToCsvStep(String exportFile) {
		// TODO Auto-generated method stub
		return null;
	}

}