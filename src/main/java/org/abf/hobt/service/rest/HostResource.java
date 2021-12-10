package org.abf.hobt.service.rest;

import org.abf.hobt.dto.Criteria;
import org.abf.hobt.dto.Organism;
import org.abf.hobt.host.HostParts;
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
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/criterias")
    public Response getHostCriterias(
        @PathParam("id") long organismId,
        @DefaultValue("0") @QueryParam("offset") int offset,
        @DefaultValue("15") @QueryParam("limit") int limit,
        @DefaultValue("id") @QueryParam("sort") String sort,
        @DefaultValue("false") @QueryParam("asc") boolean asc) {
        Hosts hosts = new Hosts();
        return super.respond(hosts.retrieveCriteria(organismId));
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getHost(@PathParam("id") long orgId) {
        Hosts hosts = new Hosts();
        return super.respond(hosts.retrieve(orgId));
    }

    @GET
    @Path("/{id}/parts")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getHostParts(@DefaultValue("false") @QueryParam("strainsOnly") boolean strainsOnly,
                                 @PathParam("id") long organismId) {
        HostParts parts = new HostParts();
        return super.respond(parts.get(organismId, strainsOnly));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createHost(Organism organism) {
        Hosts hosts = new Hosts();
        return super.respond(hosts.create(organism));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/criterias/{cid}/status")
    public Response createHostCriteria(@PathParam("id") long orgId, @PathParam("cid") long cid, Criteria criteria) {
        Hosts hosts = new Hosts();
        hosts.updateOrganismCriteriaStatus(orgId, cid, criteria.getStatus());
        return super.respond(true);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteHost(@PathParam("id") long orgId) {
        return super.respond(false);
    }
}
