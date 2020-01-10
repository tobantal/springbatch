package spring.boot.batch.processor;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import spring.boot.batch.model.KoListMemberView;

@StepScope
@Component("blankAddressProcessor")
public class BlankAddressProcessor implements ItemProcessor<KoListMemberView, KoListMemberView> {

    private boolean excludeAddress;

    public BlankAddressProcessor(@Value("#{jobParameters['excludeAddress']}") String excludeAddress) {
        this.excludeAddress = Boolean.parseBoolean(excludeAddress);
    }

	@Override
    public KoListMemberView process(KoListMemberView item) throws Exception {
        if(excludeAddress) {
            item.setKlmAddress("");
        }
		return item;
	}

}
