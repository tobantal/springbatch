package spring.boot.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import spring.boot.batch.model.Product;

@Component("blankAddressProcessor")
public class BlankAddressProcessor implements ItemProcessor<Product, Product> {

	@Override
	public Product process(Product item) throws Exception {
        item.setDescription("");
        //TODO may be mapping into new pojo
        // changed to setAddress()
		return item;
	}

}
