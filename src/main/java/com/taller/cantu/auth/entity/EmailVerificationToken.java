package com.taller.cantu.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "email_verification_token")
public class EmailVerificationToken {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false, unique = true, columnDefinition = "TEXT")
    private String token;

    @Column(name = "expiration_datetime", nullable = false)
    private LocalDateTime expiration_datetime;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "creation_datetime", nullable = false)
    private LocalDateTime creation_datetime;

    @Column(name = "update_datetime")
    private LocalDateTime update_datetime;

    @PrePersist
    protected void onCreate() {
        creation_datetime = LocalDateTime.now();
        update_datetime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        update_datetime = LocalDateTime.now();
    }
}
