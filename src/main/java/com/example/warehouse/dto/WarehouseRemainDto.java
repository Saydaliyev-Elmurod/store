package com.example.warehouse.dto;

import com.example.warehouse.model.WarehouseRemain;

public class WarehouseRemainDto {
    public Long productId;
    public String productName;
    public String sku;
    public long quantity;

    public static WarehouseRemainDto from(WarehouseRemain r) {
        WarehouseRemainDto dto = new WarehouseRemainDto();
        dto.productId = r.product != null ? r.product.id : null;
        dto.productName = r.product != null ? r.product.name : null;
        dto.sku = r.product != null ? r.product.sku : null;
        dto.quantity = r.quantity;
        return dto;
    }
}
