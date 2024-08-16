package com.taller.cantu.auth.exception;

public class RegexValidationException extends RuntimeException {

    // Constructor sin argumentos
    public RegexValidationException() {
        super();
    }

    // Constructor con un mensaje de error
    public RegexValidationException(String message) {
        super(message);
    }

    // Constructor con un mensaje de error y una causa
    public RegexValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor con una causa
    public RegexValidationException(Throwable cause) {
        super(cause);
    }
}

