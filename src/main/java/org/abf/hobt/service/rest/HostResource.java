package org.abf.hobt.service.rest;

import org.abf.hobt.dto.Organism;
import org.abf.hobt.host.Hosts;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hosts")
public class HostResource extends RestResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getHosts(
        @DefaultValue("0") @QueryParam("offset") int offset,
        @DefaultValue("15") @QueryParam("limit") int limit,
        @DefaultValue("id") @QueryParam("sort") String sort,
        @DefaultValue("false") @QueryParam("asc") boolean asc) {
        Hosts hosts = new Hosts();
        return super.respond(hosts.retrieveList(offset, limit, sort, asc));
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getHost(@PathParam("id") long orgId) {
        Hosts hosts = new Hosts();
        return super.respond(hosts.retrieve(orgId));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createHost(Organism organism) {
        Hosts hosts = new Hosts();
        return super.respond(hosts.create(organism));
    }

    @DELETE
    @Path("/{id}")
    public Response deleteHost(@PathParam("id") long orgId) {
        return super.respond(false);
    }
}
