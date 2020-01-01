package spring.boot.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import spring.boot.batch.service.ImportService;

@EnableBatchProcessing
@SpringBootApplication
public class BatchApplication {

	public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(BatchApplication.class, args);

	// generate and save data
	ProductPagingAndSortingRepository repository = ctx.getBean(ProductPagingAndSortingRepository.class);
	for(long i = 0L; i < 1_000L; i++) {
		repository.save(new spring.boot.batch.model.Product(i, String.format("Product-%d", i), new java.util.Date().toString(), 1234.5));
	}

        ImportService importService = ctx.getBean(ImportService.class);
        importService.start();

        System.exit(SpringApplication.exit(ctx));
	}

}
