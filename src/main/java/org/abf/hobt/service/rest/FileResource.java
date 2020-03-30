package org.abf.hobt.service.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

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
