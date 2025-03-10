package com.vjuliano.parse;

import com.vjuliano.util.Assert;
import com.vjuliano.response.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileParser implements IFileParser {

    private static final String DELIMITER = ",";
    private static final int NUM_COLS = 4;
    private static final int X_COL = 2;
    private static final int Y_COL = 3;

    @Override
    public GenericResponse<List<List<Boolean>>> parse(File inputFile, int scale) {
        try {
            final List<List<Boolean>> matrix = initMatrix(scale);
            final MinMaxCoords minMax = getMinMaxCoords(inputFile);

            //size of each 'pixel'
            final double xscale = (minMax.maxX - minMax.minX) / (double) scale;
            final double yscale = (minMax.maxY - minMax.minY) / (double) scale;

            final Offsets offsets = getOffsets(minMax.minX, minMax.minY);

            populateMatrix(xscale, yscale, offsets, inputFile, matrix);

            return GenericResponse.success(matrix);
        } catch (Exception ex) {
            log.error("Error parsing file", ex);
            return GenericResponse.failure(ex.getMessage());
        }
    }

    private List<List<Boolean>> initMatrix(int scale) {
        Assert.isTrue(scale > 0, "Scale must be greater than 0.  Scale: " + scale);
        final List<List<Boolean>> matrix = new ArrayList<>(scale + 1);
        for (int i = 0; i <= scale; i++) {
            List<Boolean> row = new ArrayList<>(scale + 1);
            matrix.add(row);

            for (int x = 0; x <= scale; x++) {
                row.add(false);
            }
        }

        return matrix;
    }

    private MinMaxCoords getMinMaxCoords(File inputFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            double maxX = Double.MIN_VALUE;
            double minX = Double.MAX_VALUE;
            double maxY = Double.MIN_VALUE;
            double minY = Double.MAX_VALUE;

            //skip over first line
            br.readLine();

            String line;
            while((line = br.readLine()) != null) {
                double x;
                double y;

                String[] elms = line.split(DELIMITER);
                Assert.isTrue(elms.length == NUM_COLS, "Invalid row format.  Size: " + elms.length);

                x = Double.parseDouble(elms[X_COL]);
                y = Double.parseDouble(elms[Y_COL]);

                //filter invalid rows
                if (x == 0d || y == 0d) {
                    continue;
                }

                if (x < minX) {
                    minX = x;
                }
                if (x > maxX) {
                    maxX = x;
                }

                if (y < minY) {
                    minY = y;
                }
                if (y > maxY) {
                    maxY = y;
                }
            }

            return new MinMaxCoords(minX, maxX, minY, maxY);
        }
    }

    private Offsets getOffsets(double minX, double minY) {
        //adjust to shift min coords to 0
        double xOffset;
        double yOffset;

        if (minX < 0) {
            xOffset = Math.abs(minX);
        } else {
            xOffset = 0d - minX;
        }
        if (minY < 0) {
            yOffset = Math.abs(minY);
        } else {
            yOffset = 0d - minY;
        }

        return new Offsets(xOffset, yOffset);
    }

    private void populateMatrix(double xscale, double yscale, Offsets offsets,
                                File inputFile, List<List<Boolean>> matrix) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            //skip first line
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] elms = line.split(DELIMITER);
                double x = Double.parseDouble(elms[X_COL]);
                double y = Double.parseDouble(elms[Y_COL]);

                //filter invalid rows
                if (x == 0d || y == 0d) {
                    continue;
                }

                x += offsets.xOffset;
                y += offsets.yOffset;

                matrix.get((int)(y / yscale)).set((int)(x / xscale), true);
            }
        }
    }

    private record MinMaxCoords(double minX, double maxX, double minY, double maxY){}

    private record Offsets(double xOffset, double yOffset) {}

}
