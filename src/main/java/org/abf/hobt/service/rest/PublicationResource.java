package org.abf.hobt.service.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.abf.hobt.host.publication.Publications;

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
