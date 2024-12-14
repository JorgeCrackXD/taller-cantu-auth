package com.taller.cantu.auth.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    @NotEmpty(message = "Parameter 'email' is required")
    private String email;

    @NotEmpty(message = "Parameter 'password' is required")
    private String password;
}
