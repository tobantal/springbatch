package spring.boot.batch.listener;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import spring.boot.batch.model.Product;

@StepScope
@Component("writeListener")
@Slf4j
public class WriteListener extends StepExecutionListenerSupport implements ItemWriteListener<Product> {

    private String sql;
    private int total;
    private int current;
    private JdbcOperations jdbc;

    public WriteListener(@Value("#{jobParameters['jdbcReaderSql']}") String sql,
        JdbcOperations jdbc) {
        this.sql = StringUtils.replace(sql, "*", "count(*)");
        this.jdbc = jdbc;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        total = jdbc.queryForObject(sql, Integer.class);
        log.info("BEFORE STEP TOTAL: {}", total);
    }

	@Override
	public void beforeWrite(List<? extends Product> items) {
	}

	@Override
	public void afterWrite(List<? extends Product> items) {
        current += items.size();
        log.info("Progress: {}%", (int)(current/(double)total*100));
	}

	@Override
	public void onWriteError(Exception exception, List<? extends Product> items) {
		// TODO Auto-generated method stub

	}

}