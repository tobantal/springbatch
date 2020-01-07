package spring.boot.batch.reader;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import spring.boot.batch.mapper.ProductRowMapper;
import spring.boot.batch.model.Product;

@Component
@StepScope
public class JdbcReader extends JdbcCursorItemReader<Product> {

    public JdbcReader(DataSource dataSource,
        @Value("#{jobParameters['jdbcReaderSql']}") String sql) {
        setDataSource(dataSource);
        setSql(sql); //"SELECT * FROM products"
        setRowMapper(new ProductRowMapper());
    }

}