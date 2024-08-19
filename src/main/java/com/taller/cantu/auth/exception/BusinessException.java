package com.taller.cantu.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class BusinessException extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;

    // Constructor con solo mensaje
    public BusinessException(String message) {
        super(message);
        this.message = message;
        this.httpStatus = HttpStatus.BAD_REQUEST; // Establece un HttpStatus por defecto
    }

    // Constructor con mensaje y HttpStatus
    public BusinessException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
