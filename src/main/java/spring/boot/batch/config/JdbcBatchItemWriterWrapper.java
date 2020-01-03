package spring.boot.batch.config;

import java.util.List;

import org.springframework.batch.item.database.JdbcBatchItemWriter;

import spring.boot.batch.model.Product;

public class JdbcBatchItemWriterWrapper extends JdbcBatchItemWriter<Product> {

    private int chunks = 0;

	@Override
	public void write(List<? extends Product> items) throws Exception {
        // TODO Auto-generated method stub
        System.out.println(">>>>> " + this.getClass().toGenericString() + ".write(..)[" +(chunks++) +"]");
        super.write(items);
	}


}