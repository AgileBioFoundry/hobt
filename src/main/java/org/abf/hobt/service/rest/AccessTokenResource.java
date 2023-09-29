package org.abf.hobt.service.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.abf.hobt.account.Authenticator;
import org.abf.hobt.account.SessionHandler;
import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.dto.Account;

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
        Authenticator authenticator = new Authenticator();
        try {
            Account info = authenticator.authenticate(transfer.getUserId(), transfer.getPassword());

            if (info == null) {
                String errMsg = "Authentication failed for user '" + transfer.getUserId() + "'";
                Logger.warn(errMsg);
                return respond(Response.Status.UNAUTHORIZED, errMsg);
            }

            Logger.info("User '" + transfer.getUserId() + "' successfully logged in");
            return respond(info);
        } catch (Exception e) {
            Logger.error(e);
            return respond(Response.Status.INTERNAL_SERVER_ERROR);
        }
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
        if (SessionHandler.isValidSession(sessionId))
            return Response.status(Response.Status.OK).build();
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}

