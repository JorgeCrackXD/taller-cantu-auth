package com.taller.cantu.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GlobalResponse {

    private String message;

    private Object data;

    private List<ErrorDTO> errors;
}
