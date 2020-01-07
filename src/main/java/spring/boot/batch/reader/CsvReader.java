package spring.boot.batch.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

import spring.boot.batch.model.Product;

public class CsvReader extends FlatFileItemReader<Product> {

    private final String delimeter = ";";
    private final String namesCsv = "id;name;description;price";

    public CsvReader(String importFile) {
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