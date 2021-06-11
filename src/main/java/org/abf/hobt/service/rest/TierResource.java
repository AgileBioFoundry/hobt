package org.abf.hobt.service.rest;

import org.abf.hobt.tier.Tier;
import org.abf.hobt.tier.Tiers;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tiers")
public class TierResource extends RestResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getTiers() {
        Tiers tiers = new Tiers();
        return super.respond(tiers.get());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTier(Tier tier) {
        Tiers tiers = new Tiers();
        return super.respond(tiers.create(tier));
    }

    @GET
    @Path("/{id}/criteria")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getTierCriteria(@PathParam("id") long tierId) {
        return super.respond(false);
    }
}
