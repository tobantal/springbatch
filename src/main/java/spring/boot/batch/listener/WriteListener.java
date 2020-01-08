package spring.boot.batch.listener;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.stereotype.Component;

import spring.boot.batch.model.Product;

@StepScope
@Component("writeListener")
public class WriteListener extends StepExecutionListenerSupport implements ItemWriteListener<Product> {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println(">>>>>>>>>> BEFORE STEP COMMIT COUNT: " + stepExecution.getCommitCount());
    }

	@Override
	public void beforeWrite(List<? extends Product> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterWrite(List<? extends Product> items) {
        // TODO Auto-generated method stub
        System.out.println(">>>> wrote: " + items.size());
	}

	@Override
	public void onWriteError(Exception exception, List<? extends Product> items) {
		// TODO Auto-generated method stub

	}

}