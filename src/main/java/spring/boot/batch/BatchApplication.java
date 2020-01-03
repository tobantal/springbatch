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

    //generateFakeData(ctx);

    ImportService importService = ctx.getBean(ImportService.class);
    importService.start();

    System.exit(SpringApplication.exit(ctx));
    }
    
    private static void generateFakeData(ConfigurableApplicationContext ctx) {
        ProductPagingAndSortingRepository repository = ctx.getBean(ProductPagingAndSortingRepository.class);
    long count = 100_000L;
    for(long j = 0; j < 400_000L; j += count) {
        List<Product> products = new ArrayList<>((int)count);
        String description = "DESCRIPTION";
        for(long i = 0L; i < count; i++) {
            products.add(new Product(j+i, String.format("Product-%d", j+i), description, 1234.5));
        }
        repository.saveAll(products);

        System.out.println(">>>>> System.gc()");
        System.gc();
        try {
            Thread.sleep(10_000L);
        } catch(InterruptedException ie) {
        }
    }

}
}