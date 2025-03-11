package com.vjuliano.asciiArt;

import com.vjuliano.asciiArt.draw.IAsciiDraw;
import com.vjuliano.asciiArt.parse.IFileParser;
import com.vjuliano.asciiArt.plot.IAsciiPlotter;
import com.vjuliano.asciiArt.response.GenericResponse;
import com.vjuliano.asciiArt.util.Assert;
import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.util.List;

@Slf4j
public class AsciiArt {

    private final IFileParser fileParser;
    private final IAsciiPlotter asciiPlotter;
    private final IAsciiDraw asciiDraw;

    private final File inputFile;
    private final String outputFilePath;
    private final int scale;
    private final String pixelChar;

    public AsciiArt(File inputFile, String outputFilePath, int scale, String pixelChar,
                    IFileParser fileParser, IAsciiDraw asciiDraw, IAsciiPlotter asciiPlotter) {
        this.fileParser = fileParser;
        this.asciiPlotter = asciiPlotter;
        this.asciiDraw = asciiDraw;

        this.inputFile = inputFile;
        this.outputFilePath = outputFilePath;
        this.scale = scale;
        this.pixelChar = pixelChar;
    }

    public GenericResponse<Boolean> create() {
        try {
            GenericResponse<List<List<Boolean>>> parseResponse = fileParser.parse(inputFile, scale);
            Assert.isTrue(parseResponse.isSuccess(),
                    parseResponse.isSuccess() ? "" : "Failed to parse input file: " + parseResponse.getErrorMsg());

            GenericResponse<String> plotResponse = asciiPlotter.plot(scale, pixelChar, parseResponse.getValue());
            Assert.isTrue(plotResponse.isSuccess(),
                    plotResponse.isSuccess() ? "" : "Failed to plot: " + plotResponse.getErrorMsg());

            GenericResponse<Boolean> drawResponse = asciiDraw.draw(plotResponse.getValue(), outputFilePath);
            Assert.isTrue(drawResponse.isSuccess(),
                    drawResponse.isSuccess() ? "" : "Failed to draw: " + drawResponse.getErrorMsg());

            return GenericResponse.success(true);
        } catch (Exception ex) {
            log.error("Error creating ASCII Art", ex);
            return GenericResponse.failure(ex.getMessage());
        }

    }

}
