package com.vjuliano.asciiArt.plot;

import com.vjuliano.asciiArt.response.GenericResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class AsciiPlotter implements IAsciiPlotter {

    private static final String LIGHT_PIXEL = " ";
    private static final String NEW_LINE = "\n";  //\r\n on windows

    @Override
    public GenericResponse<String> plot(int scale, String pixelChar, List<List<Boolean>> matrix) {
        try {
            StringBuilder sb = new StringBuilder();
            for (int y = scale; y >= 0; y--) {
                for (int x = 0; x < scale; x++) {
                    boolean posVal = matrix.get(x).get(y);
                    if (posVal) {
                        sb.append(pixelChar);
                    } else {
                        sb.append(LIGHT_PIXEL);
                    }
                }
                sb.append(NEW_LINE);
            }
            return GenericResponse.success(sb.toString());
        } catch (Exception ex) {
            log.error("Error plotting ascii.", ex);
            return GenericResponse.failure(ex.getMessage());
        }

    }

}
