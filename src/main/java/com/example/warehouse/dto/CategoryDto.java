package com.example.warehouse.dto;

import com.example.warehouse.model.Category;

public class CategoryDto {
    public Long id;
    public String name;
    public String description;

    public static CategoryDto from(Category c) {
        CategoryDto dto = new CategoryDto();
        dto.id = c.id;
        dto.name = c.name;
        dto.description = c.description;
        return dto;
    }
}
