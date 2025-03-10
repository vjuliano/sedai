package com.vjuliano.asciiArt;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import com.vjuliano.asciiArt.draw.IAsciiDraw;
import com.vjuliano.asciiArt.parse.IFileParser;
import com.vjuliano.asciiArt.plot.IAsciiPlotter;
import com.vjuliano.asciiArt.response.GenericResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AsciiArtTest {

    private static final File INPUT_FILE = new File("");
    private static final String OUTPUT_FILE_PATH = "outputFilePath";
    private static final int SCALE = 5;
    private static final String PIXEL_CHAR = "#";

    @Mock
    private IFileParser fileParser;
    @Mock
    private IAsciiDraw asciiDraw;
    @Mock
    private IAsciiPlotter asciiPlotter;

    private AsciiArt asciiArt;

    @BeforeEach
    public void before() {
        asciiArt = new AsciiArt(INPUT_FILE, OUTPUT_FILE_PATH, SCALE, PIXEL_CHAR,
                fileParser, asciiDraw, asciiPlotter);
    }

    @Test
    public void testCreate() {
        List<List<Boolean>> parseValue = new ArrayList<>();
        String plotValue = "plotValue";

        when(fileParser.parse(INPUT_FILE, SCALE)).thenReturn(GenericResponse.success(parseValue));
        when(asciiPlotter.plot(SCALE, PIXEL_CHAR, parseValue)).thenReturn(GenericResponse.success(plotValue));
        when(asciiDraw.draw(plotValue, OUTPUT_FILE_PATH)).thenReturn(GenericResponse.success(true));

        assertTrue(asciiArt.create().isSuccess());
    }

    @Test
    public void testCreate_parseFail() {
        String errorMsg = "errorMsg";

        when(fileParser.parse(INPUT_FILE, SCALE)).thenReturn(GenericResponse.failure(errorMsg));

        GenericResponse<Boolean> result = asciiArt.create();

        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMsg().contains(errorMsg));

        verify(asciiPlotter, never()).plot(anyInt(), anyString(), anyList());
        verify(asciiDraw, never()).draw(anyString(), anyString());
    }

    @Test
    public void testCreate_plotFail() {
        List<List<Boolean>> parseValue = new ArrayList<>();
        String errorMsg = "errorMsg";

        when(fileParser.parse(INPUT_FILE, SCALE)).thenReturn(GenericResponse.success(parseValue));
        when(asciiPlotter.plot(SCALE, PIXEL_CHAR, parseValue)).thenReturn(GenericResponse.failure(errorMsg));

        GenericResponse<Boolean> result = asciiArt.create();

        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMsg().contains(errorMsg));

        verify(asciiDraw, never()).draw(anyString(), anyString());
    }

    @Test
    public void testCreate_drawFail() {
        List<List<Boolean>> parseValue = new ArrayList<>();
        String plotValue = "plotValue";
        String errorMsg = "errorMsg";

        when(fileParser.parse(INPUT_FILE, SCALE)).thenReturn(GenericResponse.success(parseValue));
        when(asciiPlotter.plot(SCALE, PIXEL_CHAR, parseValue)).thenReturn(GenericResponse.success(plotValue));
        when(asciiDraw.draw(plotValue, OUTPUT_FILE_PATH)).thenReturn(GenericResponse.failure(errorMsg));

        GenericResponse<Boolean> result = asciiArt.create();

        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMsg().contains(errorMsg));
    }

}
