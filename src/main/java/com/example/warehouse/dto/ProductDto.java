package com.example.warehouse.dto;

import com.example.warehouse.model.Product;

public class ProductDto {
    public Long id;
    public String name;
    public String sku;
    public Long categoryId;
    public String categoryName;

    public static ProductDto from(Product p) {
        ProductDto dto = new ProductDto();
        dto.id = p.id;
        dto.name = p.name;
        dto.sku = p.sku;
        dto.categoryId = p.category != null ? p.category.id : null;
        dto.categoryName = p.category != null ? p.category.name : null;
        return dto;
    }
}
