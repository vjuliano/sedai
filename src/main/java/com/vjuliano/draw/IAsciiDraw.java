package com.vjuliano.draw;

import com.vjuliano.response.GenericResponse;

public interface IAsciiDraw {

    GenericResponse<Boolean> draw(String plot, String outputPath);

}
