package com.vjuliano.asciiArt.draw;

import com.vjuliano.asciiArt.response.GenericResponse;

public interface IAsciiDraw {

    GenericResponse<Boolean> draw(String plot, String outputPath);

}
