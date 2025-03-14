package com.vjuliano.asciiArt;

import com.vjuliano.asciiArt.draw.AsciiDraw;
import com.vjuliano.asciiArt.parse.FileParser;
import com.vjuliano.asciiArt.plot.AsciiPlotter;
import com.vjuliano.asciiArt.response.GenericResponse;
import com.vjuliano.asciiArt.util.Assert;
import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

@Slf4j
public class Main {

    private static final String DEFAULT_FILE_PATH = "../ukpostcodes.csv";
    private static final String DEFAULT_PIXEL_CHAR = "#";
    private static final int DEFAULT_SCALE = 50;

    public static void main(String[] args) {
        try {
            Scanner keyboardScanner = new Scanner(System.in);
            File inputFile = getInputFile(keyboardScanner);
            int scale = getScale(keyboardScanner);
            String pixelChar = getPixelChar(keyboardScanner);
            String outputFilePath = getOutputFilePath(keyboardScanner);

            GenericResponse<Boolean> result =
                    new AsciiArt(inputFile, outputFilePath, scale, pixelChar,
                            new FileParser(), new AsciiDraw(), new AsciiPlotter())
                            .create();
            Assert.isTrue(result.isSuccess(),
                    result.isSuccess() ? "" : "AsciiArt failed: " + result.getErrorMsg());
        } catch (Exception ex) {
            log.error("Error initializing program", ex);
            System.exit(1);
        }
        System.exit(0);
    }

    private static File getInputFile(Scanner keyboardScanner) {
        System.out.println("Enter input filepath: (" + DEFAULT_FILE_PATH + ")");

        String inputPath = keyboardScanner.nextLine();

        if (inputPath.isBlank()) {
            inputPath = DEFAULT_FILE_PATH;
        }

        Assert.isTrue(Files.exists(Paths.get(inputPath)), "Invalid path: " + inputPath);

        return new File(inputPath);
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