package spring.boot.batch.config;

import java.util.List;

import org.springframework.batch.item.database.JdbcBatchItemWriter;

import spring.boot.batch.model.Product;

public class JdbcBatchItemWriterWrapper extends JdbcBatchItemWriter<Product> {

	@Override
	public void write(List<? extends Product> items) throws Exception {
        // TODO Auto-generated method stub

	}


}