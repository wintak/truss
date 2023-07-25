package com.truss.csv.handlers;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class CsvFileHandler {

    private static final Logger log = LoggerFactory.getLogger(CsvFileHandler.class);
    private final InputStream inputStream;
    public CsvFileHandler() {
        this(System.in);
    }

    public CsvFileHandler(InputStream in) {
        this.inputStream = in;
    }

    public List<String[]> readCsv() {
        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            CsvParserSettings parserSettings = new CsvParserSettings();
            parserSettings.getFormat().setLineSeparator("\n");
            parserSettings.setHeaderExtractionEnabled(true);
            CsvParser parser = new CsvParser(parserSettings);
            return parser.parseAll(reader);
        } catch (IOException e) {
            log.error("Failed to read CSV data from STDIN", e);
            return Collections.emptyList();
        }
    }

    public void writeCsv(List<String[]> allRows) {
        try (Writer writer = new OutputStreamWriter(System.out, StandardCharsets.UTF_8)) {
            CsvWriterSettings writerSettings = new CsvWriterSettings();
            CsvWriter writerCsv = new CsvWriter(writer, writerSettings);
            allRows.forEach(writerCsv::writeRow);
            writer.flush();
        } catch (IOException e) {
            log.error("Failed to write CSV data to STDOUT", e);
        }
    }
}
