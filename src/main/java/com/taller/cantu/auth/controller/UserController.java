package com.taller.cantu.auth.controller;

import com.taller.cantu.auth.dto.GlobalResponse;
import com.taller.cantu.auth.dto.UserRegisterDTO;
import com.taller.cantu.auth.exception.BusinessException;
import com.taller.cantu.auth.service.UserService;
import com.taller.cantu.auth.utils.ResponseUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<GlobalResponse> registerUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            GlobalResponse response = userService.registerUser(userRegisterDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception ex) {
            GlobalResponse exceptionResponse;
            if (ex instanceof BusinessException businessException) {
                exceptionResponse = ResponseUtils.buildExceptionResponse(businessException, "Error registering user");
                return new ResponseEntity<>(exceptionResponse, businessException.getHttpStatus());
            }
            log.error("Error registering user", ex);
            exceptionResponse = ResponseUtils.buildExceptionResponse(ex, "Error registering user");
            return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/activate")
    public ResponseEntity<GlobalResponse> activateUser(@RequestParam String email, @RequestParam String code) {
        try {
            GlobalResponse response = userService.activateUser(email, code);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            GlobalResponse exceptionResponse;
            if (ex instanceof BusinessException businessException) {
                exceptionResponse = ResponseUtils.buildExceptionResponse(businessException, "Error activating user");
                return new ResponseEntity<>(exceptionResponse, businessException.getHttpStatus());
            }
            log.error("Error activating user", ex);
            exceptionResponse = ResponseUtils.buildExceptionResponse(ex, "Error activating user");
            return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
