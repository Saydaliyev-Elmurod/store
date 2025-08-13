package com.example.warehouse.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "products")
public class Product extends PanacheEntity {

    @Column(nullable = false, unique = true)
    public String name;

    @Column(nullable = false, unique = true, length = 128)
    public String sku;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    public Category category;

    @Column(name = "created_at")
    public Instant createdAt;

    @Column(name = "created_by")
    public String createdBy;

    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
