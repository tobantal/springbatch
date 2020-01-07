package spring.boot.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import spring.boot.batch.constants.AppConstants;
import spring.boot.batch.step.StepFactory;

@Component
@RequiredArgsConstructor
public class JobFactoryImpl implements JobFactory {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepFactory stepFactory;

    @Override
    public Job createComplexJob(String jobName, String importFile, String exportFile) {
        return jobBuilderFactory.get(jobName)
                .incrementer(new RunIdIncrementer())
                .flow(stepFactory.createCsvToDbStep(importFile))
                .on(AppConstants.STEP_COMPLETED).to(stepFactory.createDbToCsvStep(exportFile))
                .on(AppConstants.STEP_FAILED).fail()
                .end()
                .build();
    }
}
