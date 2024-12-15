package com.taller.cantu.auth.controller;

import com.taller.cantu.auth.dto.GlobalResponse;
import com.taller.cantu.auth.dto.UserActivationDTO;
import com.taller.cantu.auth.dto.UserLoginDTO;
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

import java.util.UUID;

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
    public ResponseEntity<GlobalResponse> activateUser(@Valid @RequestBody UserActivationDTO userActivationDTO) {
        try {
            GlobalResponse response = userService.activateUser(userActivationDTO);
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

    @PostMapping("/login")
    public ResponseEntity<GlobalResponse> authenticate(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        try {
            GlobalResponse response = userService.loginUser(userLoginDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            GlobalResponse exceptionResponse;
            if (ex instanceof BusinessException businessException) {
                exceptionResponse = ResponseUtils.buildExceptionResponse(businessException, "Error trying to log in.");
                return new ResponseEntity<>(exceptionResponse, businessException.getHttpStatus());
            }
            log.error("Error trying to log in", ex);
            exceptionResponse = ResponseUtils.buildExceptionResponse(ex, "Error trying to log in");
            return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/block")
    public ResponseEntity<GlobalResponse> blockUser(@RequestParam String email, @RequestParam boolean blocked) {
        try {
            GlobalResponse response = userService.blockUser(email, blocked);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            GlobalResponse exceptionResponse;
            if (ex instanceof BusinessException businessException) {
                exceptionResponse = ResponseUtils.buildExceptionResponse(businessException, "Error trying to block the user.");
                return new ResponseEntity<>(exceptionResponse, businessException.getHttpStatus());
            }
            log.error("Error trying to block user.", ex);
            exceptionResponse = ResponseUtils.buildExceptionResponse(ex, "Error trying to log in.");
            return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
