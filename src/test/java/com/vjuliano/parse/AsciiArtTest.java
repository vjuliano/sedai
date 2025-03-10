package com.vjuliano.parse;

import com.vjuliano.draw.IAsciiDraw;
import com.vjuliano.plot.IAsciiPlotter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AsciiArtTest {

    @Mock
    private IFileParser fileParser;
    @Mock
    private IAsciiDraw asciiDraw;
    @Mock
    private IAsciiPlotter asciiPlotter;

    @Test
    public void testCreate() {

    }

    @Test
    public void testCreate_parseFail() {

    }

    @Test
    public void testCreate_plotFail() {

    }

    @Test
    public void testCreate_drawFail() {

    }

}
