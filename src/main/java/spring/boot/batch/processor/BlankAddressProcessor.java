package spring.boot.batch.processor;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import spring.boot.batch.model.Product;

@StepScope
@Component("blankAddressProcessor")
public class BlankAddressProcessor implements ItemProcessor<Product, Product> {

    private boolean excludeAddress;

    public BlankAddressProcessor(@Value("#{jobParameters['excludeAddress']}") String excludeAddress) {
        this.excludeAddress = Boolean.parseBoolean(excludeAddress);
    }

	@Override
	public Product process(Product item) throws Exception {
        if(excludeAddress) {
            item.setDescription("");
        }
        //TODO may be mapping into new pojo
        // changed to setAddress()
		return item;
	}

}
