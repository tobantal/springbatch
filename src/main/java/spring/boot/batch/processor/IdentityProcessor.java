package spring.boot.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import spring.boot.batch.model.Product;

@Component("identityProcessor")
public class IdentityProcessor implements ItemProcessor<Product, Product> {

	@Override
	public Product process(Product item) throws Exception {
		return item;
	}

}
