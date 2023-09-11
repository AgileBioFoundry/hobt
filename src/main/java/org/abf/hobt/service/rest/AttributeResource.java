package org.abf.hobt.service.rest;

import org.abf.hobt.attribute.Attributes;
import org.abf.hobt.dto.OrganismAttribute;
import org.abf.hobt.host.HostAttributes;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/attributes")
public class AttributeResource extends RestResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAttribute(OrganismAttribute organismAttribute) {
        String userId = getUserId(true);
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
                                  @DefaultValue("false") @QueryParam("asc") boolean asc,
                                  @QueryParam("organism") long organismId) {
        String userId = getUserId(true);
        if (organismId > 0) {
            log(userId, "retrieving attributes for host " + organismId);
            HostAttributes hostAttributes = new HostAttributes(organismId);
            return super.respond(hostAttributes.get(userId));
        } else {
            log(userId, "retrieving all attributes");
            Attributes attributes = new Attributes();
            return super.respond(attributes.get(userId, offset, limit, asc));
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response updateAttribute(@PathParam("id") long attributeId, OrganismAttribute organismAttribute) {
        String userId = getUserId(true);
        log(userId, "updating attribute " + attributeId);
        Attributes attributes = new Attributes();
        return super.respond(attributes.update(attributeId, userId, organismAttribute));
    }
}
