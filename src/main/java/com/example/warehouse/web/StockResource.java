package com.example.warehouse.web;

import com.example.security.UserPrincipal;
import com.example.warehouse.dto.StockMoveRequest;
import com.example.warehouse.service.StockService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/api/stock")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Stock")
public class StockResource {

    @Inject
    StockService stockService;

    @Inject
    UserPrincipal principal;

    @POST
    @Path("/income")
    @RolesAllowed("admin")
    public Response income(StockMoveRequest req) {
        stockService.income(req, principal);
        return Response.noContent().build();
    }

    @POST
    @Path("/outcome")
    @RolesAllowed("admin")
    public Response outcome(StockMoveRequest req) {
        stockService.outcome(req, principal);
        return Response.noContent().build();
    }
}
