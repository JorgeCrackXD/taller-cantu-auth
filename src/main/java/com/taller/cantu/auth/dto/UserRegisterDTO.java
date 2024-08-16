package com.taller.cantu.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {

    @NotEmpty(message = "Parameter 'first_name' is required")
    private String first_name;

    @NotEmpty(message = "Parameter 'last_name' is required")
    private String last_name;

    @NotEmpty(message = "Parameter 'full_name' is required")
    private String full_name;

    @NotNull(message = "Parameter 'birth_date' is required")
    private Date birth_date;

    @NotEmpty(message = "Parameter 'email' is required")
    private String email;

    @NotEmpty(message = "Parameter 'password' is required")
    private String password;
}
