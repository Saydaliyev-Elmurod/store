package com.example.warehouse.web;

import com.example.security.UserPrincipal;
import com.example.warehouse.dto.CategoryDto;
import com.example.warehouse.dto.CreateCategoryRequest;
import com.example.warehouse.service.CategoryService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.List;

@Path("/api/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Categories")
public class CategoryResource {

    @Inject
    CategoryService service;

    @Inject
    UserPrincipal principal;

    @GET
    public List<CategoryDto> list() {
        return service.listAll();
    }

    @GET
    @Path("/{id}")
    public CategoryDto get(@PathParam("id") Long id) {
        return service.get(id);
    }

    @POST
    @RolesAllowed("admin")
    public Response create(CreateCategoryRequest req) {
        CategoryDto dto = service.create(req, principal);
        return Response.created(URI.create("/api/categories/" + dto.id)).entity(dto).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public void delete(@PathParam("id") Long id) {
        service.delete(id, principal);
    }
}
