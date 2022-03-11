package org.abf.hobt.service.rest;

import org.abf.hobt.host.publication.Publications;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/publications")
public class PublicationResource extends RestResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getPublications(
        @DefaultValue("0") @QueryParam("offset") int offset,
        @DefaultValue("15") @QueryParam("limit") int limit,
        @DefaultValue("id") @QueryParam("sort") String sort,
        @DefaultValue("false") @QueryParam("asc") boolean asc) {
        Publications publications = new Publications();
        return super.respond(publications.getAll(offset, limit, sort, asc));
    }
}
