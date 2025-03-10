package com.vjuliano.parse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.vjuliano.draw.AsciiDraw;
import com.vjuliano.response.GenericResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AsciiDrawTest {
    private static final String TEST_FILE_PATH = "testDraw.csv";

    private AsciiDraw asciiDraw = new AsciiDraw();

    @BeforeEach
    public void before() {
        File file = new File(TEST_FILE_PATH);
        file.delete();
    }

    @Test
    public void testDraw() throws FileNotFoundException, IOException {
        String plot = "plotString";

        GenericResponse<Boolean> result = asciiDraw.draw(plot, TEST_FILE_PATH);
        assertTrue(result.isSuccess());

        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists());
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            assertNotNull(line);
            assertEquals(plot, line);
        }
    }

    @Test
    public void testDraw_outputPathBlank() {

    }

    @Test
    public void testDraw_fileExists() {

    }
}
