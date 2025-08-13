package com.example.warehouse.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;
import java.time.Instant;

@Entity
@Table(name = "categories")
public class Category extends PanacheEntity {

    @Column(nullable = false, unique = true)
    public String name;

    @Column(length = 1000)
    public String description;

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
