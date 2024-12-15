package com.taller.cantu.auth.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBlockedDTO {

    @Column(name = "user_id")
    private UUID user_id;

    @Column(name = "user_email")
    private String user_email;

    @Column(name = "blocked")
    private Boolean blocked;
}
