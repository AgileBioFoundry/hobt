package org.abf.hobt.service.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.abf.hobt.dto.Criteria;
import org.abf.hobt.dto.Organism;
import org.abf.hobt.dto.TierStatus;
import org.abf.hobt.host.HostAttributes;
import org.abf.hobt.host.HostExperiments;
import org.abf.hobt.host.HostParts;
import org.abf.hobt.host.Organisms;
import org.abf.hobt.host.publication.HostPublications;
import org.abf.hobt.host.publication.Publication;
import org.abf.hobt.host.publication.Publications;
import org.abf.hobt.host.status.HostStatus;
import org.abf.hobt.service.protocols.Protocols;

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
                                 @DefaultValue("0") @QueryParam("start") int offset,
                                 @DefaultValue("15") @QueryParam("limit") int limit,
                                 @DefaultValue("id") @QueryParam("sort") String sort,
                                 @DefaultValue("false") @QueryParam("asc") boolean asc,
                                 @PathParam("id") long organismId) {
        String userId = getUserId(false);
        HostParts parts = new HostParts(userId);
        return super.respond(parts.get(organismId, strainsOnly, offset, limit, asc));
    }

    @GET
    @Path("/{id}/experiments")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getHostExperiments(@PathParam("id") long organismId) {
        String userId = getUserId(false);
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
    @Path("/{id}/protocols")
    public Response getHostProtocols(
        @PathParam("id") long organismId,
        @DefaultValue("0") @QueryParam("offset") int offset,
        @DefaultValue("15") @QueryParam("limit") int limit,
        @DefaultValue("id") @QueryParam("sort") String sort,
        @DefaultValue("false") @QueryParam("asc") boolean asc) {
        Protocols protocols = new Protocols();
        return super.respond(protocols.getByOrganism(organismId, offset, limit, sort, asc));
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
        String userId = getUserId(true);
        log(userId, "creating new publication");
        HostPublications hostPublications = new HostPublications(organismId, userId);
        return super.respond(hostPublications.create(publication));
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/tiers/status")
    public Response updateTierCompletionStatus(@PathParam("id") long hostId, TierStatus tierStatus) {
        String userId = getUserId(true);
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

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/attributes/values")
    public Response updateHostAttributeValues(@PathParam("id") long hostId, Organism organism) {
        String userId = getUserId(true);
        log(userId, "update host attribute values for host " + hostId);
        HostAttributes hostAttributes = new HostAttributes(hostId);
        hostAttributes.update(organism);
        return super.respond(true);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/attributes/values")
    public Response getHostAttributeValues(@PathParam("id") long hostId) {
        HostAttributes hostAttributes = new HostAttributes(hostId);
        return super.respond(hostAttributes.getValues());
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/attributes/{att_id}")
    public Response deleteHostAttribute(@PathParam("id") long hostId, @PathParam("att_id") long attributeId) {
        String userId = getUserId(true);
        HostAttributes hostAttributes = new HostAttributes(hostId);
        return super.respond(hostAttributes.delete(userId, attributeId));
    }
}
