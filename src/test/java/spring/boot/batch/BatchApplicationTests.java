package spring.boot.batch;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.sql.DataSource;

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

        DataSource ds = ctx.getBean(DataSource.class);
        assertNotNull(ds);
	}

}
