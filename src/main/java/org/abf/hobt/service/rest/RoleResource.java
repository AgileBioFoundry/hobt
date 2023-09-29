package org.abf.hobt.service.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.abf.hobt.dto.Permission;
import org.abf.hobt.dto.Role;
import org.abf.hobt.role.RolePermissions;
import org.abf.hobt.role.Roles;

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
        String userId = getUserId(true);
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

    @GET
    @Path("/{id}/members")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getRoleMembers(@PathParam("id") long roleId) {
        String userId = getUserId(true);
        log(userId, "get role members");
        Roles roles = new Roles();
        return super.respond(roles.getRoleMembers(roleId));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRole(Role role) {
        Roles roles = new Roles();
        return super.respond(roles.create(role));
    }

    @POST
    @Path("/{id}/permissions")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPermission(@PathParam("id") long roleId, Permission permission) {
        RolePermissions rolePermissions = new RolePermissions(roleId);
        permission = rolePermissions.add(permission);
        return super.respond(permission);
    }
}
