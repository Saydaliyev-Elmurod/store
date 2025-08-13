package com.example.warehouse.service;

import com.example.security.UserPrincipal;
import com.example.warehouse.dto.CreateProductRequest;
import com.example.warehouse.dto.ProductDto;
import com.example.warehouse.model.Category;
import com.example.warehouse.model.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class ProductService {
    private static final Logger log = Logger.getLogger(ProductService.class);

    public List<ProductDto> listAll() {
        return Product.<Product>listAll().stream().map(ProductDto::from).toList();
    }

    public ProductDto get(Long id) {
        Product p = Product.findById(id);
        if (p == null) {
            throw new WebApplicationException("Product not found", Response.Status.NOT_FOUND);
        }
        return ProductDto.from(p);
    }

    @Transactional
    public ProductDto create(CreateProductRequest req, UserPrincipal principal) {
        if (req == null || req.name == null || req.name.isBlank() || req.sku == null || req.sku.isBlank() || req.categoryId == null) {
            throw new WebApplicationException("Invalid product payload", Response.Status.BAD_REQUEST);
        }
        long dupSku = Product.count("lower(sku) = ?1", req.sku.toLowerCase());
        if (dupSku > 0) {
            throw new WebApplicationException("SKU already exists", Response.Status.CONFLICT);
        }
        Category cat = Category.findById(req.categoryId);
        if (cat == null) {
            throw new WebApplicationException("Category not found", Response.Status.BAD_REQUEST);
        }
        Product p = new Product();
        p.name = req.name.trim();
        p.sku = req.sku.trim();
        p.category = cat;
        p.createdBy = principal.getUserId();
        p.persist();
        log.infof("User %s created product '%s' (SKU: %s)", principal.getUsername(), p.name, p.sku);
        return ProductDto.from(p);
    }

    @Transactional
    public void delete(Long id, UserPrincipal principal) {
        Product p = Product.findById(id);
        if (p == null) {
            throw new WebApplicationException("Product not found", Response.Status.NOT_FOUND);
        }
        p.delete();
        log.infof("User %s deleted product '%s' (SKU: %s)", principal.getUsername(), p.name, p.sku);
    }
}
