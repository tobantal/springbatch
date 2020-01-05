package spring.boot.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import spring.boot.batch.model.Product;

public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet res, int rowNum) throws SQLException {
        return new Product(res.getLong("product_id"), res.getString("name"), res.getString("description"),
                res.getDouble("price"));
    }
}
