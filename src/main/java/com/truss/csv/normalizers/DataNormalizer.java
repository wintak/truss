package com.truss.csv.normalizers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.DoubleBuffer;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class DataNormalizer {

    private static final Logger log = LoggerFactory.getLogger(DataNormalizer.class);

    public List<String[]> normalizeData(List<String[]> allRows) {
        allRows.forEach(this::normalizeRow);
        return allRows;
    }

    public void normalizeRow(String[] row) {
        try {
            normalizeTimestamp(row);
            normalizeZipCode(row);
            normalizeFullName(row);
            normalizeDurations(row);
        } catch (Exception e) {
            log.warn("Failed to normalize row. Replacing with Unicode Replacement Character.", e);
            e.printStackTrace();
            Arrays.fill(row, "\uFFFD"); //remove this
        }
    }

    private void normalizeTimestamp(String[] row) {
        LocalDateTime ldt = LocalDateTime.parse(row[0], DateTimeFormatter.ofPattern("M/d/yy h:mm:ss a", Locale.ENGLISH));
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("US/Pacific"));
        ZonedDateTime converted = zdt.withZoneSameInstant(ZoneId.of("US/Eastern"));
        row[0] = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(converted);
    }

    private void normalizeZipCode(String[] row) {
        row[2] = String.format("%05d", Integer.parseInt(row[2]));
    }

    private void normalizeFullName(String[] row) {
        row[3] = row[3].toUpperCase();
    }

    private void normalizeDurations(String[] row) {
        Double a = parseDuration(row[4]);
        Double b = parseDuration(row[5]);
        Double c = a + b;

        row[4] = String.valueOf(a);
        row[5] = String.valueOf(b);
        row[6] = NumberFormat.getInstance().format(c);
    }

    private double parseDuration(String duration) {
        String[] parts = duration.split(":");
        double hours = Double.parseDouble(parts[0]);
        double minutes = Double.parseDouble(parts[1]);
        double seconds = Double.parseDouble(parts[2]);
        return hours * 3600 + minutes * 60 + seconds;
    }
}
