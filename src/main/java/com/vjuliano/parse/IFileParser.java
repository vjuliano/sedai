package com.vjuliano.parse;

import com.vjuliano.response.GenericResponse;

import java.io.File;
import java.util.List;

public interface IFileParser {

    GenericResponse<List<List<Boolean>>> parse(File inputFile, int scale);

}
