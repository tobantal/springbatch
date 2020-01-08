package spring.boot.batch.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import spring.boot.batch.constants.AppConstants;
import spring.boot.batch.model.Product;

@StepScope
@Component("csvReader")
public class CsvReader extends FlatFileItemReader<Product> {

    public CsvReader(@Value("#{jobParameters['importFile']}") String importFile) {
        setResource(new ClassPathResource(importFile));
        setLineMapper(new DefaultLineMapper<Product>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(AppConstants.EXTRACT_FIELDS);
                setDelimiter(AppConstants.DELIMETER);
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Product>() {{
                setTargetType(Product.class);
            }});
        }});
    }
}