package spring.boot.batch.writer;

import javax.sql.DataSource;

import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import spring.boot.batch.model.Product;

public class JdbcWriter extends JdbcBatchItemWriter<Product> {

    public JdbcWriter(DataSource dataSource) {
        setDataSource(dataSource);
        setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        setSql("INSERT INTO products " +
                "(product_id, name, description, price) " +
                "VALUES (:id, :name, :description, :price)");
    }

}