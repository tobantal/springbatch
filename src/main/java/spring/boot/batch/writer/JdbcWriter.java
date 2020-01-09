package spring.boot.batch.writer;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import spring.boot.batch.model.Product;

@StepScope
@Component("jdbcWriter")
public class JdbcWriter extends JdbcBatchItemWriter<Product> {

    public JdbcWriter(@Qualifier("postgres") DataSource dataSource,
        @Value("#{jobParameters['jdbcWriterSql']}") String sql) {
        setDataSource(dataSource);
        setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        setSql(sql);
    }

}