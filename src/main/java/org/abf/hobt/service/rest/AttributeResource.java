package org.abf.hobt.service.rest;

import org.abf.hobt.attribute.Attributes;
import org.abf.hobt.dto.OrganismAttribute;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/attributes")
public class AttributeResource extends RestResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAttribute(OrganismAttribute organismAttribute) {
        String userId = getUserId();
        log(userId, "creating new attribute");
        Attributes attributes = new Attributes();
        return super.respond(attributes.create(userId, organismAttribute));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAttributes(@DefaultValue("0") @QueryParam("start") int offset,
                                 @DefaultValue("15") @QueryParam("limit") int limit,
                                 @DefaultValue("id") @QueryParam("sort") String sort,
                                 @DefaultValue("false") @QueryParam("asc") boolean asc) {
        String userId = getUserId();
        log(userId, "retrieving all attributes");
        Attributes attributes = new Attributes();
        return super.respond(attributes.get(userId, offset, limit, asc));
    }
}
