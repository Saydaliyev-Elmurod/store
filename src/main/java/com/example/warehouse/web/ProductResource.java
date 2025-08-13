package com.example.warehouse.web;

import com.example.security.UserPrincipal;
import com.example.warehouse.dto.CreateProductRequest;
import com.example.warehouse.dto.ProductDto;
import com.example.warehouse.service.ProductService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.List;

@Path("/api/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Products")
public class ProductResource {

    @Inject
    ProductService service;

    @Inject
    UserPrincipal principal;

    @GET
    public List<ProductDto> list() {
        return service.listAll();
    }

    @GET
    @Path("/{id}")
    public ProductDto get(@PathParam("id") Long id) {
        return service.get(id);
    }

    @POST
    @RolesAllowed("admin")
    public Response create(CreateProductRequest req) {
        ProductDto dto = service.create(req, principal);
        return Response.created(URI.create("/api/products/" + dto.id)).entity(dto).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public void delete(@PathParam("id") Long id) {
        service.delete(id, principal);
    }
}
