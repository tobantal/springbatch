package spring.boot.batch.reader;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import spring.boot.batch.mapper.KoListMemberViewMapper;
import spring.boot.batch.model.KoListMemberView;

@StepScope
@Component("jdbcReader")
public class JdbcReader extends JdbcCursorItemReader<KoListMemberView> {

    public JdbcReader(DataSource dataSource, // @Qualifier("oracle")
        @Value("#{jobParameters['jdbcReaderSql']}") String sql) {
        setDataSource(dataSource);
        setSql(sql);
        setRowMapper(new KoListMemberViewMapper());
    }

}