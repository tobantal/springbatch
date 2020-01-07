package spring.boot.batch.service;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.boot.batch.job.JobFactory;

@Service
@RequiredArgsConstructor
public class ImportServiceImpl implements ImportService {

    private final JobLauncher jobLauncher;
    //private final JobFactory jobFactory;

    @Qualifier("complexJob")
    private final Job job;

    @Override
    public void start() { // <- JobParameters
        try {
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
            jobParametersBuilder.addString("importFile", "import.csv");
            jobParametersBuilder.addString("exportFile", "products-export.csv");
            jobParametersBuilder.addString("jdbcReaderSql", "SELECT * FROM products where product_id >= 3 and product_id <= 10");

            JobExecution jobExecution = jobLauncher.run(
                job, //jobFactory.createComplexJob("complex job"),
                jobParametersBuilder.toJobParameters());

            BatchStatus status;
            do {
                status = jobExecution.getStatus();
                System.out.println(status);
                Thread.sleep(1000);
            } while (status != BatchStatus.COMPLETED);

        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
