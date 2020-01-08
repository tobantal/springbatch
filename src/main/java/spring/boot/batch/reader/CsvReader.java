package spring.boot.batch.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import spring.boot.batch.model.Product;

@Component("csvReader")
@StepScope
public class CsvReader extends FlatFileItemReader<Product> {

    private final String delimeter = ";";
    private final String namesCsv = "id;name;description;price";

    public CsvReader(@Value("#{jobParameters['importFile']}") String importFile) {
        setResource(new ClassPathResource(importFile));
        setLineMapper(new DefaultLineMapper<Product>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(namesCsv.split(delimeter));
                setDelimiter(delimeter);
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Product>() {{
                setTargetType(Product.class);
            }});
        }});
    }
}