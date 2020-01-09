package spring.boot.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import spring.boot.batch.constants.AppConstants;

@Component
@RequiredArgsConstructor
public class JobFactoryImpl implements JobFactory {

    private final JobBuilderFactory jobBuilderFactory;

    @Qualifier("csvToDbStep")
    private final Step csvToDbStep;

    @Qualifier("dbToCsvStep")
    private final Step dbToCsvStep;

    @Override
    public Job createComplexJob(String jobName) {
        return jobBuilderFactory.get(jobName)
                .incrementer(new RunIdIncrementer())
                //.flow(csvToDbStep)
                //.on(AppConstants.STEP_COMPLETED).to(dbToCsvStep)
                //.on(AppConstants.STEP_FAILED).fail()
                .flow(dbToCsvStep)
                .end()
                .build();
    }
}
