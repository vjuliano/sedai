package com.vjuliano.response;

import com.vjuliano.util.Assert;
import lombok.Getter;

@Getter
public class GenericResponse<T> {

    private final T value;
    private final String errorMsg;
    private final boolean success;

    private GenericResponse(T value) {
        this.value = value;
        errorMsg = null;
        success = true;
    }

    private GenericResponse(String errorMsg) {
        this.errorMsg = errorMsg;
        value = null;
        success = false;
    }

    public static <T> GenericResponse<T> success(T value) {
        Assert.isNotNull(value, "Success response value can not be null");
        return new GenericResponse<>(value);
    }

    public static <T> GenericResponse<T> failure(String errorMsg) {
        return new GenericResponse<>(errorMsg);
    }
}
