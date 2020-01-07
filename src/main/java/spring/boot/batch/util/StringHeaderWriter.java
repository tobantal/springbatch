package spring.boot.batch.util;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StringHeaderWriter implements FlatFileHeaderCallback {

    private final String header;

    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write(header);
    }
}