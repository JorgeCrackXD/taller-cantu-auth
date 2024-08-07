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
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "model", length = 100)
    private String model;

    @Column(name = "creation_datetime", nullable = false)
    private LocalDateTime creation_datetime;

    @Column(name = "update_datetime")
    private LocalDateTime update_datetime;

    @ManyToOne
    @JoinColumn(name = "product_type_id", nullable = false)
    private ProductType productType;

    @ManyToOne
    @JoinColumn(name = "product_brand_id", nullable = false)
    private ProductBrand productBrand;

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
