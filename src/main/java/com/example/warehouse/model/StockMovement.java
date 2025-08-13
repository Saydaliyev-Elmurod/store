package com.example.warehouse.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "stock_movements")
public class StockMovement extends PanacheEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    public Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false, length = 16)
    public MovementType movementType;

    @Column(nullable = false)
    public long quantity;

    @Column(name = "before_qty")
    public Long beforeQty;

    @Column(name = "after_qty")
    public Long afterQty;

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
