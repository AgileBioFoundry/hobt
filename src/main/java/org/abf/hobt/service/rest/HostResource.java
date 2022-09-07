package org.abf.hobt.service.rest;

import org.abf.hobt.dto.Criteria;
import org.abf.hobt.dto.Organism;
import org.abf.hobt.dto.TierStatus;
import org.abf.hobt.host.HostExperiments;
import org.abf.hobt.host.HostParts;
import org.abf.hobt.host.Organisms;
import org.abf.hobt.host.publication.HostPublications;
import org.abf.hobt.host.publication.Publication;
import org.abf.hobt.host.publication.Publications;
import org.abf.hobt.host.status.HostStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hosts")
public class HostResource extends RestResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/statistics")
    public Response getHostStatistics(@PathParam("id") long organismId) {
        Organisms organisms = new Organisms();
        return super.respond(organisms.getStatistics(organismId));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getHosts(
        @DefaultValue("0") @QueryParam("offset") int offset,
        @DefaultValue("15") @QueryParam("limit") int limit,
        @DefaultValue("id") @QueryParam("sort") String sort,
        @DefaultValue("false") @QueryParam("asc") boolean asc) {
        Organisms organisms = new Organisms();
        return super.respond(organisms.retrieveList(offset, limit, sort, asc));
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
        Organisms organisms = new Organisms();
        return super.respond(organisms.retrieveCriteria(organismId));
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getHost(@PathParam("id") long orgId) {
        Organisms organisms = new Organisms();
        return super.respond(organisms.retrieve(orgId));
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

    @GET
    @Path("/{id}/experiments")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getHostExperiments(@PathParam("id") long organismId) {
        HostExperiments experiments = new HostExperiments();
        return super.respond(experiments.get(organismId));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createHost(Organism organism) {
        Organisms organisms = new Organisms();
        return super.respond(organisms.create(organism));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/criterias/{cid}/status")
    public Response createHostCriteria(@PathParam("id") long orgId, @PathParam("cid") long cid, Criteria criteria) {
        Organisms organisms = new Organisms();
        organisms.updateOrganismCriteriaStatus(orgId, cid, criteria.getStatus());
        return super.respond(true);
    }

    // TODO : mark tier completed  PUT "{id}/criterias/{cid}/completed

    @DELETE
    @Path("/{id}")
    public Response deleteHost(@PathParam("id") long orgId) {
        return super.respond(false);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/publications")
    public Response getHostPublications(
        @PathParam("id") long organismId,
        @DefaultValue("0") @QueryParam("offset") int offset,
        @DefaultValue("15") @QueryParam("limit") int limit,
        @DefaultValue("id") @QueryParam("sort") String sort,
        @DefaultValue("false") @QueryParam("asc") boolean asc,
        @QueryParam("privileged") Boolean privileged) {
        Publications publications = new Publications();
        return super.respond(publications.getByOrganism(organismId, offset, limit, sort, asc, privileged));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/publications")
    public Response createHostPublication(@PathParam("id") long organismId, Publication publication) {
        String userId = getUserId();
        log(userId, "creating new publication");
        HostPublications hostPublications = new HostPublications(organismId, userId);
        return super.respond(hostPublications.create(publication));
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/tiers/status")
    public Response updateTierCompletionStatus(@PathParam("id") long hostId, TierStatus tierStatus) {
        String userId = getUserId();
        log(userId, "updating organism " + hostId + " status");
        HostStatus hostStatus = new HostStatus(hostId, userId);
        return super.respond(hostStatus.update(tierStatus));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/tiers/status")
    public Response getTierStatus(@PathParam("id") long hostId) {
        log("", "retrieving organism " + hostId + " tiers status");
        HostStatus hostStatus = new HostStatus(hostId, null);
        return super.respond(hostStatus.get());
    }
}
