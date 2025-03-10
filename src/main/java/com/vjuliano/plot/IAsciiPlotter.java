package com.vjuliano.plot;

import com.vjuliano.response.GenericResponse;

import java.util.List;

public interface IAsciiPlotter {

    GenericResponse<String> plot(int scale, String pixelChar, List<List<Boolean>> matrix);

}
