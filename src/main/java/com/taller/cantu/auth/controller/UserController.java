package com.taller.cantu.auth.controller;

import com.taller.cantu.auth.dto.GlobalResponse;
import com.taller.cantu.auth.dto.UserRegisterDTO;
import com.taller.cantu.auth.service.UserService;
import com.taller.cantu.auth.utils.ResponseUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            GlobalResponse exceptionResponse = ResponseUtils.buildExceptionResponse(ex, "Error registering user");
            return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
