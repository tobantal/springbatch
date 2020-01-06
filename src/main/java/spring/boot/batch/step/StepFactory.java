package spring.boot.batch.step;

import org.springframework.batch.core.Step;

public interface StepFactory {

    Step createCsvToDbStep(String importFile);

    Step createDbToCsvStep(String exportFile);
}