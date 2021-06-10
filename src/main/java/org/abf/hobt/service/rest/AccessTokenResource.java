package org.abf.hobt.service.rest;

import org.abf.hobt.dto.Account;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * API for access tokens (also session id for the user interface)
 *
 * @author Hector Plahar
 */
@Path("/accesstokens")
public class AccessTokenResource extends RestResource {

    /**
     * Creates a new access token for the user referenced in the parameter, after the credentials
     * (username and password) are validated. If one already exists, it is invalidated
     *
     * @param transfer wraps username and password
     * @return account information including a valid session id if credentials validate
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(final Account transfer) {
        return super.respond(false);
    }

    /**
     * Invalidates the current session information.
     */
    @DELETE
    public Response deleteToken() {
        return super.respond(false);
    }

    /**
     * Retrieve account information for user referenced by session id
     *
     * @return account information for session if session is valid, null otherwise
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        return super.respond(false);
    }
}

