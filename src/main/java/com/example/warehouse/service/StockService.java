package com.example.warehouse.service;

import com.example.security.UserPrincipal;
import com.example.warehouse.dto.StockMoveRequest;
import com.example.warehouse.model.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

@ApplicationScoped
public class StockService {
    private static final Logger log = Logger.getLogger(StockService.class);

    @Inject
    WarehouseRemainService remainService;

    @Transactional
    public void income(StockMoveRequest req, UserPrincipal principal) {
        if (req == null || req.productId == null || req.quantity <= 0) {
            throw new WebApplicationException("Invalid stock income payload", Response.Status.BAD_REQUEST);
        }
        Product product = Product.findById(req.productId);
        if (product == null) {
            throw new WebApplicationException("Product not found", Response.Status.NOT_FOUND);
        }
        WarehouseRemain remain = remainService.getOrCreate(product);
        long before = remain.quantity;
        long after = before + req.quantity;
        remain.quantity = after;
        remain.updatedBy = principal.getUserId();
        remain.persist();

        StockMovement mv = new StockMovement();
        mv.product = product;
        mv.movementType = MovementType.INCOME;
        mv.quantity = req.quantity;
        mv.beforeQty = before;
        mv.afterQty = after;
        mv.createdBy = principal.getUserId();
        mv.persist();

        log.infof("User %s income %d for product %s (before=%d, after=%d)", principal.getUsername(), req.quantity, product.sku, before, after);
    }

    @Transactional
    public void outcome(StockMoveRequest req, UserPrincipal principal) {
        if (req == null || req.productId == null || req.quantity <= 0) {
            throw new WebApplicationException("Invalid stock outcome payload", Response.Status.BAD_REQUEST);
        }
        Product product = Product.findById(req.productId);
        if (product == null) {
            throw new WebApplicationException("Product not found", Response.Status.NOT_FOUND);
        }
        WarehouseRemain remain = remainService.getOrCreate(product);
        long before = remain.quantity;
        if (before < req.quantity) {
            throw new WebApplicationException("Not enough stock", Response.Status.CONFLICT);
        }
        long after = before - req.quantity;
        remain.quantity = after;
        remain.updatedBy = principal.getUserId();
        remain.persist();

        StockMovement mv = new StockMovement();
        mv.product = product;
        mv.movementType = MovementType.OUTCOME;
        mv.quantity = req.quantity;
        mv.beforeQty = before;
        mv.afterQty = after;
        mv.createdBy = principal.getUserId();
        mv.persist();

        log.infof("User %s outcome %d for product %s (before=%d, after=%d)", principal.getUsername(), req.quantity, product.sku, before, after);
    }
}
