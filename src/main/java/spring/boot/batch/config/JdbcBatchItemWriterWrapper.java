package spring.boot.batch.config;

import java.util.List;

import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import spring.boot.batch.model.Product;

public class JdbcBatchItemWriterWrapper extends JdbcBatchItemWriter<Product> {

    private int chunks = 0;

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
        // TODO Auto-generated method stub
        System.out.println(">>>>> " + this.getClass().toGenericString() + ".write(..)[" +(chunks++) +"]");
        super.write(items);
    }

    @AfterWrite
    public void afterWrite( ) {
        System.out.println("AFTER WRITE: JdbcBatchItemWriter");
    }

    @AfterStep
    public void afterStep( ) {
        System.out.println("AFTER STEP: JdbcBatchItemWriter");
    }

}