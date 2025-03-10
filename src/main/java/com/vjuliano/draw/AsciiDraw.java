package com.vjuliano.draw;

import com.vjuliano.response.GenericResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class AsciiDraw implements IAsciiDraw {

    private static final String LIGHT_PIXEL = " ";

    @Override
    public GenericResponse<Boolean> draw(int scale, String pixelChar, List<List<Boolean>> matrix) {
        try {
            for (int y = scale; y >= 0; y--) {
                for (int x = 0; x < scale; x++) {
                    boolean posVal = matrix.get(x).get(y);
                    if (posVal) {
                        System.out.print(pixelChar);
                    } else {
                        System.out.print(LIGHT_PIXEL);
                    }
                }
                System.out.println();
            }
            return GenericResponse.success(true);
        } catch (Exception ex) {
            log.error("Error drawing ascii.", ex);
            return GenericResponse.failure(ex.getMessage());
        }

    }

}
