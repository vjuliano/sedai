package com.vjuliano.asciiArt.draw;

import com.vjuliano.asciiArt.response.GenericResponse;
import com.vjuliano.asciiArt.util.Assert;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;

@Slf4j
public class AsciiDraw implements IAsciiDraw {

    @Override
    public GenericResponse<Boolean> draw(String plot, String outputPath) {
        try {
            if (outputPath.isBlank()) {
                System.out.println(plot);
            } else {
                File file = new File(outputPath);
                Assert.isTrue(file.createNewFile(), "File already exists");

                try (FileWriter fw = new FileWriter(file)) {
                    fw.write(plot);
                }
            }

            return GenericResponse.success(true);
        } catch (Exception ex) {
            log.error("Error drawing ASCII art", ex);
            return GenericResponse.failure(ex.getMessage());
        }

    }
}
