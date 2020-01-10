package spring.boot.batch;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootTest
class BatchApplicationTests {

    @Autowired
    ConfigurableApplicationContext ctx;


	@Test
	void contextLoads() {
        assertNotNull(ctx);
	}

}
