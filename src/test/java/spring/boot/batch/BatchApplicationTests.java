package spring.boot.batch;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import spring.boot.batch.model.Product;
import spring.boot.batch.processor.IdentityProcessor;
import spring.boot.batch.step.StepFactory;
import spring.boot.batch.step.StepFactoryImpl;

@SpringBootTest
class BatchApplicationTests {

    @Autowired
    ConfigurableApplicationContext ctx;

    @Autowired
    StepFactoryImpl stepFactory;

	@Test
	void contextLoads() {
        assertNotNull(ctx);

        DataSource ds = ctx.getBean(DataSource.class);
        assertNotNull(ds);

        StepBuilderFactory sbf = stepFactory.getStepBuilderFactory();
        assertNotNull(sbf);

        IdentityProcessor processor = stepFactory.getProcessor();
        assertNotNull(processor);
	}

}
