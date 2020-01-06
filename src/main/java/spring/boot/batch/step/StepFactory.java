package spring.boot.batch.step;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;

public interface StepFactory {

    Step createCsvToDbStep(String importFile, DataSource dataSource);

    Step createDbToCsvStep(String exportFile);
}