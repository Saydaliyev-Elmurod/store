package com.example.warehouse.web;

import com.example.warehouse.dto.WarehouseRemainDto;
import com.example.warehouse.service.WarehouseRemainService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/api/remains")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Warehouse Remains")
public class WarehouseRemainResource {

    @Inject
    WarehouseRemainService service;

    @GET
    public List<WarehouseRemainDto> list() {
        return service.listAll();
    }

    @GET
    @Path("/{productId}")
    public WarehouseRemainDto get(@PathParam("productId") Long productId) {
        return service.getByProductId(productId);
    }
}
