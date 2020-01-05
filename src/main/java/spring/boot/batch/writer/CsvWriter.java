package spring.boot.batch.writer;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.core.io.FileSystemResource;

import spring.boot.batch.model.Product;
import spring.boot.batch.util.StringHeaderWriter;

public class CsvWriter extends FlatFileItemWriter<Product> {

    public CsvWriter(String exportFilePath) {
        setResource(new FileSystemResource(exportFilePath));

        String exportFileHeader = String.format("REPORT\nDATE:%s\nID;NAME;DESCRIPTION;PRICE", new java.util.Date());
        StringHeaderWriter headerWriter = new StringHeaderWriter(exportFileHeader);
        setHeaderCallback(headerWriter);

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