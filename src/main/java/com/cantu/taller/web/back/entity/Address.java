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
@Table(name = "address")
public class Address {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street", length = 100, nullable = false)
    private String street;

    @Column(name = "interior_number", length = 10)
    private String interior_number;

    @Column(name = "exterior_number", length = 10, nullable = false)
    private String exterior_number;

    @Column(name = "zip_code", nullable = false)
    private Integer zip_number;

    @Column(name = "colony", length = 100, nullable = false)
    private String colony;

    @Column(name = "city", length = 100, nullable = false)
    private String city;

    @Column(name = "state", length = 100, nullable = false)
    private String state;

    @Column(name = "creation_datetime", nullable = false)
    private LocalDateTime creation_datetime;

    @Column(name = "update_datetime")
    private LocalDateTime update_datetime;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

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
