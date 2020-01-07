package spring.boot.batch.reader;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.stereotype.Component;

import spring.boot.batch.mapper.ProductRowMapper;
import spring.boot.batch.model.Product;

@Component
public class JdbcReader extends JdbcCursorItemReader<Product> {

    public JdbcReader(DataSource dataSource) {
        setDataSource(dataSource);
        setSql("SELECT * FROM products");
        setRowMapper(new ProductRowMapper());
    }

}