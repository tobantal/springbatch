package spring.boot.batch.wrapper;

import java.util.List;

import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import lombok.RequiredArgsConstructor;
import spring.boot.batch.model.Product;

@RequiredArgsConstructor
public class JdbcBatchItemWriterWrapper extends JdbcBatchItemWriter<Product> {

    private final JdbcBatchItemWriter<Product> writer;
    private final int steps;

    private int step = 1;

    @BeforeStep
    public void beforeStep( ) {
        System.out.println("BEFORE STEP: JdbcBatchItemWriter");
    }

    @BeforeWrite
    public void beforeWrite( ) {
        System.out.println("BEFORE WRITE: JdbcBatchItemWriter");
    }

	@Override
	public void write(List<? extends Product> items) throws Exception {
        writer.write(items);
    }

    @AfterWrite
    public void afterWrite( ) {
        //System.out.println("AFTER WRITE: JdbcBatchItemWriter");
        System.out.println(String.format("AFTER WRITE: STEP[%d / %d]", step++, steps));
    }

    @AfterStep
    public void afterStep( ) {
        System.out.println("AFTER STEP: JdbcBatchItemWriter");
    }

}