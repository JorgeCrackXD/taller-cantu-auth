package com.taller.cantu.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserActivationDTO {

    @NotNull(message = "Parameter 'user_id' is required")
    private UUID user_id;

    @NotEmpty(message = "Parameter 'code' is required")
    private String code;
}
