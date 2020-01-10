package spring.boot.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import spring.boot.batch.model.KoListMemberView;

@Component("identityProcessor")
public class IdentityProcessor implements ItemProcessor<KoListMemberView, KoListMemberView> {

	@Override
    public KoListMemberView process(KoListMemberView item) throws Exception {
		return item;
	}

}
