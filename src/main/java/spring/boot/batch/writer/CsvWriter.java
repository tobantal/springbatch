package spring.boot.batch.writer;

import javax.annotation.PostConstruct;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import spring.boot.batch.model.Product;
import spring.boot.batch.util.StringHeaderWriter;

@Component
@RequiredArgsConstructor
public class CsvWriter extends FlatFileItemWriter<Product> {

    @PostConstruct
    public void init() {
        String exportFileHeader = String.format("REPORT\nDATE:%s\nID;NAME;DESCRIPTION;PRICE", new java.util.Date());
        StringHeaderWriter headerWriter = new StringHeaderWriter(exportFileHeader);
        setHeaderCallback(headerWriter);

        String exportFilePath = "products-export.csv";
        setResource(new FileSystemResource(exportFilePath));

        LineAggregator<Product> lineAggregator = createLineAggregator();
        setLineAggregator(lineAggregator);
    }

    private LineAggregator<Product> createLineAggregator() {
        DelimitedLineAggregator<Product> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");

        FieldExtractor<Product> fieldExtractor = createFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);

        return lineAggregator;
    }

    private FieldExtractor<Product> createFieldExtractor() {
        BeanWrapperFieldExtractor<Product> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] { "id", "name", "description", "price" });
        return extractor;
    }

}