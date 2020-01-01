package spring.boot.batch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import spring.boot.batch.service.ImportService;
import spring.boot.batch.repository.ProductPagingAndSortingRepository;
import spring.boot.batch.model.Product;

@EnableBatchProcessing
@SpringBootApplication
public class BatchApplication {

	public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(BatchApplication.class, args);

	// generate and save data
    ProductPagingAndSortingRepository repository = ctx.getBean(ProductPagingAndSortingRepository.class);

    long count = 105_000L;
    List<Product> products = new ArrayList<>((int)count);
    int gcCounter = 0;
    String description = "DESCRIPTION";
    for(long i = 5000L; i < count; i++) {
		products.add(new Product(i, String.format("Product-%d", i), description, 1234.5));
        if(++gcCounter%100_000==0) {
            System.gc();
            try {
                Thread.sleep(3_000L);
            } catch(InterruptedException ie) {

            }
        }
    }
    repository.saveAll(products);

    ImportService importService = ctx.getBean(ImportService.class);
    importService.start();

    System.exit(SpringApplication.exit(ctx));
	}

}
