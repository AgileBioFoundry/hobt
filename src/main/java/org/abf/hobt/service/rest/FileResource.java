package org.abf.hobt.service.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

/**
 * Rest resource for handling file downloads
 *
 * @author Hector Plahar
 */
@Path("/file")
public class FileResource extends RestResource {
    @GET
    @Path("{uuid}/{type}")
    public Response downloadSequence(@PathParam("uuid") final String recordId, @PathParam("type") final String downloadType) {
        return super.respond(false);
    }
}
