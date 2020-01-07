package spring.boot.batch.wrapper;

import java.util.List;

import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.boot.batch.model.Product;

@RequiredArgsConstructor
@Slf4j
public class JdbcBatchItemWriterWrapper extends JdbcBatchItemWriter<Product> {

    private final JdbcBatchItemWriter<Product> writer;
    private final int steps;

    private int step = 1;

    @BeforeStep
    public void beforeStep( ) {
        log.info("BEFORE STEP: JdbcBatchItemWriter");
    }

	@Override
	public void write(List<? extends Product> items) throws Exception {
        writer.write(items);
    }

    @AfterWrite
    public void afterWrite( ) {
        log.info(String.format("AFTER WRITE: STEP[%d / %d]", step++, steps));
    }

    @AfterStep
    public void afterStep( ) {
        log.info("AFTER STEP: JdbcBatchItemWriter");
    }

}