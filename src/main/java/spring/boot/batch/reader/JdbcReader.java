package spring.boot.batch.reader;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import spring.boot.batch.mapper.ProductRowMapper;
import spring.boot.batch.model.Product;

@Component
//@RequiredArgsConstructor
public class JdbcReader extends JdbcCursorItemReader<Product> {

    public JdbcReader(DataSource dataSource) {
        setDataSource(dataSource);
        setSql("SELECT * FROM products where product_id >= 0");
        setRowMapper(new ProductRowMapper());
    }

}