package org.abf.hobt.service.rest;

import org.abf.hobt.dto.Role;
import org.abf.hobt.role.Roles;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/roles")
public class RoleResource extends RestResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getRoles(
        @DefaultValue("0") @QueryParam("offset") int offset,
        @DefaultValue("15") @QueryParam("limit") int limit,
        @DefaultValue("id") @QueryParam("sort") String sort,
        @DefaultValue("false") @QueryParam("asc") boolean asc) {
        Roles roles = new Roles();
        return super.respond(roles.list(offset, limit, sort, asc));
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getRole(@PathParam("id") long roleId) {
        Roles roles = new Roles();
        return super.respond(roles.retrieve(roleId));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRole(Role role) {
        Roles roles = new Roles();
        return super.respond(roles.create(role));
    }
}
