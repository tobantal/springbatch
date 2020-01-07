package spring.boot.batch.job;

import org.springframework.batch.core.Job;

public interface JobFactory {

    Job createComplexJob(String jobName);
}
