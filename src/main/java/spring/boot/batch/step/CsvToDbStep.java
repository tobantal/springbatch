package spring.boot.batch.step;

import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Component;

import spring.boot.batch.constants.AppConstants;

@Component("csvToDbStep")
public class CsvToDbStep implements Step {

	@Override
	public String getName() {
		return AppConstants.STEP_CSV_TO_DB;
	}

	@Override
	public boolean isAllowStartIfComplete() {
		return true;
	}

	@Override
	public int getStartLimit() {
		return 0;
	}

	@Override
	public void execute(StepExecution stepExecution) throws JobInterruptedException {
        // TODO Auto-generated method stub
        //stepExecution.
	}

}

/*

public Step stepCsvToDb() {
        return stepBuilderFactory.get(AppConstants.STEP_CSV_TO_DB)
                .<Product, Product>chunk(100)
                .reader(readerCsv())
                .processor(identityProcessor)
                .writer(writerDB())
                .build();
    }

*/