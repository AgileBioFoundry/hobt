package org.abf.hobt.service.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.abf.hobt.dto.Criteria;
import org.abf.hobt.tier.Rule;
import org.abf.hobt.tier.Tier;
import org.abf.hobt.tier.Tiers;

@Path("/tiers")
public class TierResource extends RestResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getTiers(@DefaultValue("true") @QueryParam("includeCriteria") boolean includeCriteria) {
        Tiers tiers = new Tiers();
        return super.respond(tiers.get(includeCriteria));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTier(Tier tier) {
        Tiers tiers = new Tiers();
        return super.respond(tiers.create(tier));
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTier(@PathParam("id") long id, Tier updatedTier) {
        Tiers tiers = new Tiers();
        return super.respond(tiers.update(id, updatedTier));
    }

    @GET
    @Path("/{id}/rules")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getTierRules(@PathParam("id") long tierId) {
        Tiers tiers = new Tiers();
        return super.respond(tiers.getRules(tierId));
    }

    @POST
    @Path("/{id}/rules")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRule(@PathParam("id") long tierId, Rule rule) {
        Tiers tiers = new Tiers();
        rule = tiers.addRule(tierId, rule);
        return super.respond(rule);
    }

    @GET
    @Path("/{id}/criteria")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getTierCriteria(@PathParam("id") long tierId) {
        return super.respond(false);
    }

    @POST
    @Path("/{id}/criteria")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCriteriaToTier(@PathParam("id") long tierId, Criteria criteria) {
        Tiers tiers = new Tiers();
        criteria = tiers.addCriteria(tierId, criteria);
        return super.respond(criteria);
    }

    @PUT
    @Path("/{id}/criteria/{cid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTierCriteria(@PathParam("id") long tierId, @PathParam("cid") long criteriaId, Criteria update) {
        Tiers tiers = new Tiers();
        update = tiers.updateTierCriteria(tierId, criteriaId, update);
        return super.respond(update);
    }

    @DELETE
    @Path("/{id}/criteria/{cid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteTierCriteria(@PathParam("id") long tierId, @PathParam("cid") long criteriaId) {
        Tiers tiers = new Tiers();
        return super.respond(tiers.deleteTierCriteria(tierId, criteriaId));
    }

    @PUT
    @Path("/{id}/index/{indexId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTierIndex(@PathParam("id") long tierId, @PathParam("indexId") int indexId) {
        Tiers tiers = new Tiers();
        return super.respond(tiers.updateIndex(tierId, indexId));
    }

    @GET
    @Path("/{id}/completed")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCompletedStatus(@PathParam("id") long tierId) {
        Tiers tiers = new Tiers();
        return super.respond(false);
    }

    @PUT
    @Path("/{id}/completed")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCompletedStatus(@PathParam("id") long tierId) {
        Tiers tiers = new Tiers();
        return super.respond(false);
    }
}
