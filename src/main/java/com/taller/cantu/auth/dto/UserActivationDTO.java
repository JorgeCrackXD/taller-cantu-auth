package com.taller.cantu.auth.dto;

import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "Parameter 'user_id' is required")
    private UUID user_id;

    @NotEmpty(message = "Parameter 'code' is required")
    private String code;
}
