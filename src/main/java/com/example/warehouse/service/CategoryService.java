package com.example.warehouse.service;

import com.example.security.UserPrincipal;
import com.example.warehouse.dto.CategoryDto;
import com.example.warehouse.dto.CreateCategoryRequest;
import com.example.warehouse.model.Category;
import io.quarkus.hibernate.orm.panache.Panache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class CategoryService {
    private static final Logger log = Logger.getLogger(CategoryService.class);

    public List<CategoryDto> listAll() {
        return Category.<Category>listAll().stream().map(CategoryDto::from).toList();
    }

    public CategoryDto get(Long id) {
        Category c = Category.findById(id);
        if (c == null) {
            throw new WebApplicationException("Category not found", Response.Status.NOT_FOUND);
        }
        return CategoryDto.from(c);
    }

    @Transactional
    public CategoryDto create(CreateCategoryRequest req, UserPrincipal principal) {
        if (req == null || req.name == null || req.name.isBlank()) {
            throw new WebApplicationException("Category name is required", Response.Status.BAD_REQUEST);
        }
        long existing = Category.count("lower(name) = ?1", req.name.toLowerCase());
        if (existing > 0) {
            throw new WebApplicationException("Category already exists", Response.Status.CONFLICT);
        }
        Category c = new Category();
        c.name = req.name.trim();
        c.description = req.description;
        c.createdBy = principal.getUserId();
        c.persist();
        log.infof("User %s created category '%s'", principal.getUsername(), c.name);
        return CategoryDto.from(c);
    }

    @Transactional
    public void delete(Long id, UserPrincipal principal) {
        Category c = Category.findById(id);
        if (c == null) {
            throw new WebApplicationException("Category not found", Response.Status.NOT_FOUND);
        }
        c.delete();
        log.infof("User %s deleted category '%s'", principal.getUsername(), c.name);
    }
}
