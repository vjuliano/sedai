package com.vjuliano;

import com.vjuliano.draw.AsciiDraw;
import com.vjuliano.draw.IAsciiDraw;
import com.vjuliano.parse.FileParser;
import com.vjuliano.parse.IFileParser;
import com.vjuliano.response.GenericResponse;
import com.vjuliano.util.Assert;
import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class Main {

    private static final String DEFAULT_FILE_PATH = "./ukpostcodes.csv";
    private static final String DEFAULT_PIXEL_CHAR = "#";
    private static final int DEFAULT_SCALE = 50;

    public static void main(String[] args) {
        try {
            final Scanner keyboardScanner = new Scanner(System.in);
            final IFileParser fileParser = new FileParser();
            final IAsciiDraw asciiDraw = new AsciiDraw();
            final File inputFile = getInputFile(keyboardScanner);
            final int scale = getScale(keyboardScanner);
            final String pixelChar = getPixelChar(keyboardScanner);
            final String outputFilePath = getOutputFilePath(keyboardScanner);

            final GenericResponse<List<List<Boolean>>> parseResponse = fileParser.parse(inputFile, scale);
            Assert.isTrue(parseResponse.isSuccess(),
                    parseResponse.isSuccess() ? "" : "Failed to parse input file: " + parseResponse.getErrorMsg());

            final GenericResponse<Boolean> drawResponse = asciiDraw.draw(scale, pixelChar, parseResponse.getValue());
            Assert.isTrue(drawResponse.isSuccess(), drawResponse.isSuccess() ? "" : drawResponse.getErrorMsg());
        } catch (Exception ex) {
            log.error("Error initializing program", ex);
            System.exit(1);
        }
    }

    private static File getInputFile(Scanner keyboardScanner) {
        System.out.println("Enter input filepath: (" + DEFAULT_FILE_PATH + ")");

        String inputPath = keyboardScanner.nextLine();

        if (inputPath.isBlank()) {
            inputPath = DEFAULT_FILE_PATH;
        }
        final File inputFile = new File(inputPath);
        Assert.isTrue(inputFile.isFile(), "Invalid path: " + inputPath);

        return inputFile;
    }

    private static int getScale(Scanner keyboardScanner) {
        System.out.println("Enter desired number of dimension pixels: (" + DEFAULT_SCALE + ")");

        String input = keyboardScanner.nextLine();

        return input.isBlank() ? DEFAULT_SCALE : Integer.parseInt(input);
    }

    private static String getPixelChar(Scanner keyboardScanner) {
        System.out.println("Enter desired dark pixel character: (" + DEFAULT_PIXEL_CHAR + ")");

        String input = keyboardScanner.nextLine();

        return input.isBlank() ? DEFAULT_PIXEL_CHAR : input;
    }

    private static String getOutputFilePath(Scanner keyboardScanner) {
        System.out.println("Enter output file path, or <Return> for stdout: (stdout)");

        return keyboardScanner.nextLine();
    }

}