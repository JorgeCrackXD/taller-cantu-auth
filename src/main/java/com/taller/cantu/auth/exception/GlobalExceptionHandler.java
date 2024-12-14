package com.taller.cantu.auth.exception;

import com.taller.cantu.auth.dto.ErrorDTO;
import com.taller.cantu.auth.dto.GlobalResponse;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        GlobalResponse response = new GlobalResponse();
        response.setMessage("Invalid Request Data");
        response.setErrors(new ArrayList<>());

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        for (FieldError error : fieldErrors) {
            ErrorDTO errorDTO = new ErrorDTO(error.getCode(), error.getDefaultMessage());
            response.getErrors().add(errorDTO);
        }

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse> handleUnexptectedErrors(Exception ex) {
        GlobalResponse response = new GlobalResponse();
        response.setMessage("Unexpected Internal Server Error.");
        response.setErrors(new ArrayList<>());

        ErrorDTO errorDTO = new ErrorDTO("NO CODE.", ex.getMessage());
        response.getErrors().add(errorDTO);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
