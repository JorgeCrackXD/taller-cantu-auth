package com.taller.cantu.auth.controller;

import com.taller.cantu.auth.dto.GlobalResponse;
import com.taller.cantu.auth.dto.UserRegisterDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @PostMapping
    public ResponseEntity<GlobalResponse> registerUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.ok(new GlobalResponse("User registered successfully", null, null));
    }
}
