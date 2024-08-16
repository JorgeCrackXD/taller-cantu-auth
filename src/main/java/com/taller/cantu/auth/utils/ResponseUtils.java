package com.taller.cantu.auth.utils;

import com.taller.cantu.auth.dto.ErrorDTO;
import com.taller.cantu.auth.dto.GlobalResponse;

import java.util.List;

public class ResponseUtils {

    public static GlobalResponse buildExceptionResponse(Exception ex, String message) {
        List<ErrorDTO> errors = List.of(new ErrorDTO("ERROR", ex.getMessage()));
        return new GlobalResponse(message, null, errors);
    }
}
