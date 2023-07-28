package com.truss.csv.normalizers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataNormalizerTest {
    private DataNormalizer dataNormalizer;

    @BeforeEach
    public void setUp() {
        dataNormalizer = new DataNormalizer();
    }

    @Test
    void testNormalizeData() {

        String[] row = {"4/1/23 12:00:00 AM", "Some City", "1234", "Some Name", "1:00:00", "1:00:00", "0:00:00"};

        dataNormalizer.normalizeRow(row);

        assertEquals("2023-04-01T03:00:00.000-04:00", row[0]);
        assertEquals("Some City", row[1]);
        assertEquals("01234", row[2]);
        assertEquals("SOME NAME", row[3]);
    }
}
