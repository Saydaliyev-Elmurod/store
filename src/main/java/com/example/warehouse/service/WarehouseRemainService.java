package com.example.warehouse.service;

import com.example.warehouse.dto.WarehouseRemainDto;
import com.example.warehouse.model.Product;
import com.example.warehouse.model.WarehouseRemain;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class WarehouseRemainService {

    public List<WarehouseRemainDto> listAll() {
        return WarehouseRemain.<WarehouseRemain>listAll().stream().map(WarehouseRemainDto::from).toList();
    }

    public WarehouseRemainDto getByProductId(Long productId) {
        WarehouseRemain r = findByProductId(productId);
        if (r == null) {
            throw new WebApplicationException("Remain not found", Response.Status.NOT_FOUND);
        }
        return WarehouseRemainDto.from(r);
    }

    @Transactional
    public WarehouseRemain getOrCreate(Product product) {
        WarehouseRemain r = WarehouseRemain.find("product", product).firstResult();
        if (r == null) {
            r = new WarehouseRemain();
            r.product = product;
            r.quantity = 0;
            r.persist();
        }
        return r;
    }

    public WarehouseRemain findByProductId(Long productId) {
        Product p = Product.findById(productId);
        if (p == null) {
            throw new WebApplicationException("Product not found", Response.Status.NOT_FOUND);
        }
        return WarehouseRemain.find("product", p).firstResult();
    }
}
