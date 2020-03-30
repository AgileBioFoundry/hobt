package org.abf.hobt.service.rest;

import org.abf.hobt.service.ice.search.SearchQuery;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST resource for searching. Supports keyword search with query params for filtering and advanced search
 *
 * @author Hector Plahar
 */
@Path("/search")
public class SearchResource extends RestResource {

    @Path("/{partId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findPart(@PathParam("partId") String partId) {
        return super.respond(false);
    }

    @Path("/{partId}/sequence")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findPartSequence(@PathParam("partId") String partId) {
        return super.respond(false);
    }

    /**
     * Advanced Search. The use of post is mostly for the sequence string for blast which can get
     * very long and results in a 413 status code if sent via GET
     *
     * @param query parameters to the search
     * @return results of the search
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response search(SearchQuery query) {
        return super.respond(false);
    }
}
