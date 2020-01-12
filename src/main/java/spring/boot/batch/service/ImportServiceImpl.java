package spring.boot.batch.service;

import java.util.Date;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.boot.batch.job.JobFactory;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportServiceImpl implements ImportService {

    private final JobLauncher jobLauncher;
    private final JobFactory jobFactory;

    @Override
    @Async
    public void start() { // <- JobParameters
        try {
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
            jobParametersBuilder.addString("importFile", "import.csv");
            jobParametersBuilder.addString("exportFile", "products-export.csv");
            jobParametersBuilder.addString("jdbcReaderSql", "SELECT * FROM products where product_id >= 2 and product_id <= 77");
            jobParametersBuilder.addString("jdbcWriterSql", "INSERT INTO products " +
                "(product_id, name, description, price) " +
                "VALUES (:id, :name, :description, :price)");
            jobParametersBuilder.addDate("start", new Date());
            jobParametersBuilder.addString("excludeAddress", "false");

            JobExecution jobExecution = jobLauncher.run(
                jobFactory.createComplexJob("complex job"),
                jobParametersBuilder.toJobParameters());

            BatchStatus status;
            do {
                status = jobExecution.getStatus();
                System.out.println(status);
                Thread.sleep(1000);
                // try 3 times if problems exist
                //jobExecution.stop();
            } while (status != BatchStatus.COMPLETED);
            log.info("ExportService has been finished");
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
