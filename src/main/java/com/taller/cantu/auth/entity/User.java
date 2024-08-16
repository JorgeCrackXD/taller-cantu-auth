package com.taller.cantu.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "first_name", length = 100, nullable = false)
    private String first_name;

    @Column(name = "last_name", length = 100, nullable = false)
    private String last_name;

    @Column(name = "full_name", length = 100, nullable = false)
    private String full_name;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "birth_date", nullable = false)
    private LocalDateTime birth_date;

    @Column(name = "creation_datetime", nullable = false)
    private LocalDateTime creation_datetime;

    @Column(name = "update_datetime")
    private LocalDateTime update_datetime;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private UserRole role;

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
