package com.cantu.taller.web.back.entity;

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
@Table(name = "user_address")
public class UserAddress {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_datetime", nullable = false)
    private LocalDateTime creation_datetime;

    @Column(name = "update_datetime")
    private LocalDateTime update_datetime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

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
