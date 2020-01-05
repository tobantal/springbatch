package spring.boot.batch.reader;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import spring.boot.batch.mapper.ProductRowMapper;
import spring.boot.batch.model.Product;

//@Component
@RequiredArgsConstructor
public class JdbcReader extends JdbcCursorItemReader<Product> {

    //@Autowired
    private final DataSource dataSource; //FIXME: autowired problem

    private final String sql = "SELECT * FROM products where product_id >= 0";

    private final RowMapper rowMapper = new ProductRowMapper();
}