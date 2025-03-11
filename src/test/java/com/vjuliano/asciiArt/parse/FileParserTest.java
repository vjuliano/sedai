package com.vjuliano.asciiArt.parse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.vjuliano.asciiArt.response.GenericResponse;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.List;

public class FileParserTest {

    private static final String TEST_INPUT_FILE_PATH = "testFile.csv";
    private static final String EMPTY_FILE_PATH = "emptyFile.csv";
    private static final String INVALID_NUM_COLS_FILE_PATH = "invNumCols.csv";
    private static final String INVALID_ROWS_FILE_PATH = "invRows.csv";
    private static final int SCALE = 4;

    private final IFileParser fileParser = new FileParser();

    @Test
    public void testParse() {
        File file = new File(TEST_INPUT_FILE_PATH);
        assertTrue(file.exists());

        GenericResponse<List<List<Boolean>>> result = fileParser.parse(file, SCALE);
        assertTrue(result.isSuccess());

        List<List<Boolean>> matrix = result.getValue();
        assertEquals(SCALE + 1, matrix.size());
        matrix.forEach(x -> assertEquals(SCALE + 1, x.size()));
    }

    @Test
    public void testParse_scaleZero() {

    }

    @Test
    public void testParse_emptyFile() {

    }

    @Test
    public void testParse_invalidNumCols() {

    }

    @Test
    public void testParse_invalidRows() {

    }

}
