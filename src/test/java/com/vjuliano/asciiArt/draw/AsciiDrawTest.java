package com.vjuliano.asciiArt.draw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.vjuliano.asciiArt.response.GenericResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AsciiDrawTest {
    private static final String TEST_FILE_PATH = "testDraw.csv";
    private static final String PLOT_STR = "plotStr";

    private final AsciiDraw asciiDraw = new AsciiDraw();

    @BeforeEach
    public void before() {
        File file = new File(TEST_FILE_PATH);
        file.delete();
    }

    @Test
    public void testDraw() throws IOException {
        GenericResponse<Boolean> result = asciiDraw.draw(PLOT_STR, TEST_FILE_PATH);
        assertTrue(result.isSuccess());

        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists());
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            assertNotNull(line);
            assertEquals(PLOT_STR, line);
        }
    }

    @Test
    public void testDraw_fileExists() throws IOException {
        File file = new File(TEST_FILE_PATH);
        file.createNewFile();

        GenericResponse<Boolean> result = asciiDraw.draw(PLOT_STR, TEST_FILE_PATH);

        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMsg().contains("File already exists"));
    }
}
