package com.truss.csv;

import com.truss.csv.handlers.CsvFileHandler;
import com.truss.csv.normalizers.DataNormalizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Normalizer {
    private static final Logger log = LoggerFactory.getLogger(Normalizer.class);

    public static void main(String[] args) {
        try {
            CsvFileHandler csvFileHandler = new CsvFileHandler();
            DataNormalizer dataNormalizer = new DataNormalizer();

            List<String[]> allRows = csvFileHandler.readCsv();
            allRows = dataNormalizer.normalizeData(allRows);
            csvFileHandler.writeCsv(allRows);

        } catch (NullPointerException e) {
            log.error("Failed to find the CSV file", e);
        }

    }
}
