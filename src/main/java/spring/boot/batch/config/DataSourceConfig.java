package spring.boot.batch.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    //@Bean
    public DataSource h2DataSource() {
        return DataSourceBuilder.create()
            .driverClassName("org.h2.Driver")
            .url("jdbc:h2:mem:test")
            .username("sa")
            .password("pas")
            .build();
    }

    // @Bean("postgres")
    public DataSource postgres() {
        return DataSourceBuilder.create().driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://localhost/bigdata").username("postgres").password("postgres").build();
    }

    // @Bean("oracle")
    public DataSource oracle() {
        return DataSourceBuilder.create().driverClassName("oracle.jdbc.OracleDriver")
                .url("jdbc:oracle:thin:@${DB_HOST}:${DB_PORT:1521}:${DB_NAME}")
                .username(" ${DB_USER}").password("${DB_USER_PASSWORD}").build();
    }

}
