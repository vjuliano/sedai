package com.vjuliano.asciiArt.parse;

import com.vjuliano.asciiArt.response.GenericResponse;

import java.io.File;
import java.util.List;

public interface IFileParser {

    GenericResponse<List<List<Boolean>>> parse(File inputFile, int scale);

}
