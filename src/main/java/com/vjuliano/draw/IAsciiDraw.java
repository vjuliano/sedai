package com.vjuliano.draw;

import com.vjuliano.response.GenericResponse;

import java.util.List;

public interface IAsciiDraw {

    GenericResponse<Boolean> draw(int scale, String pixelChar, List<List<Boolean>> matrix);

}
