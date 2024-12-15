package com.taller.cantu.auth.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginSuccessDTO {

    private String token_type;

    private String token_expiration_time;

    private String access_token;
}
