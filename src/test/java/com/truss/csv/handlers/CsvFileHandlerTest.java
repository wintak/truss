package com.truss.csv.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvFileHandlerTest {

    private CsvFileHandler csvFileHandler;

    @Mock
    private PrintStream stdout;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setOut(stdout);
        csvFileHandler = new CsvFileHandler();
    }

    @Test
    void testReadCsv() {
        String input = "Timestamp,Address,ZIP\n4/1/11 11:00:00AM,Some Address,94121";
        InputStream stdin = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        CsvFileHandler csvFileHandler = new CsvFileHandler(stdin);

        List<String[]> result = csvFileHandler.readCsv();

        assertEquals(1, result.size());
        assertArrayEquals(new String[] { "4/1/11 11:00:00AM", "Some Address", "94121" }, result.get(0));
    }

    @Test
    void testWriteCsv() {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"Value1", "Value2", "Value3"});
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        try {
            System.setOut(new PrintStream(stdout));
            csvFileHandler.writeCsv(data);
            assertTrue(stdout.toString().contains("Value1"));
        } finally {
            System.setOut(originalOut);
        }
    }
}
