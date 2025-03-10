package com.vjuliano.asciiArt.plot;

import com.vjuliano.asciiArt.response.GenericResponse;

import java.util.List;

public interface IAsciiPlotter {

    GenericResponse<String> plot(int scale, String pixelChar, List<List<Boolean>> matrix);

}
