package spring.boot.batch.writer;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import spring.boot.batch.constants.AppConstants;
import spring.boot.batch.model.KoListMemberView;
import spring.boot.batch.util.StringHeaderWriter;

@StepScope
@Component("csvWriter")
public class CsvWriter extends FlatFileItemWriter<KoListMemberView> {

    public CsvWriter(@Value("#{jobParameters['exportFile']}") String exportFile) {
        setResource(new FileSystemResource(exportFile));

        //TODO export from jobParameters
        String exportFileHeader = String.format("REPORT\nDATE:%s\n%s", new java.util.Date(),
                String.join(";", AppConstants.EXTRACT_FIELDS));
        StringHeaderWriter headerWriter = new StringHeaderWriter(exportFileHeader);
        setHeaderCallback(headerWriter);

        LineAggregator<KoListMemberView> lineAggregator = createLineAggregator();
        setLineAggregator(lineAggregator);
    }

    private LineAggregator<KoListMemberView> createLineAggregator() {
        DelimitedLineAggregator<KoListMemberView> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(AppConstants.DELIMETER);

        FieldExtractor<KoListMemberView> fieldExtractor = createFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);

        return lineAggregator;
    }

    private FieldExtractor<KoListMemberView> createFieldExtractor() {
        BeanWrapperFieldExtractor<KoListMemberView> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(AppConstants.EXTRACT_FIELDS);
        return extractor;
    }

}