package com.example.warehouse.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "warehouse_remain")
public class WarehouseRemain extends PanacheEntity {

    @OneToOne(optional = false)
    @JoinColumn(name = "product_id", unique = true)
    public Product product;

    @Column(nullable = false)
    public long quantity;

    @Column(name = "updated_at")
    public Instant updatedAt;

    @Column(name = "updated_by")
    public String updatedBy;

    @PrePersist
    @PreUpdate
    void touch() {
        updatedAt = Instant.now();
    }
}
