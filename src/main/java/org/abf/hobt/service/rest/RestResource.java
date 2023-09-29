package org.abf.hobt.service.rest;

import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.abf.hobt.account.SessionHandler;
import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.servlet.ErrorResult;

/**
 * Parent class for all rest resource objects
 *
 * @author Hector Plahar
 */
public class RestResource {

    private final String AUTHENTICATION_HEADER_NAME = "X-HOBT-Authentication-SessionId";

    @HeaderParam(value = AUTHENTICATION_HEADER_NAME)
    protected String sessionId;

    protected String getUserId(boolean require) {
        String userId = SessionHandler.getUserIdBySession(sessionId);
        if (userId == null && require)
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
                .entity(new ErrorResult("Your session has expired or was invalidated. Please login again."))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build());
        return userId;
    }

    protected Response respond(Object object) {
        if (object == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(object).build();
    }

    protected Response respond(Response.Status status, String errorMessage) {
        ErrorResult errorResult = new ErrorResult(errorMessage);
        return Response.status(status).entity(errorResult).build();
    }

    protected Response respond(Response.Status status) {
        return Response.status(status).build();
    }

    protected Response respond(boolean success) {
        if (success)
            return Response.status(Response.Status.OK).build();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Used to log user actions
     *
     * @param userId  unique user identifier
     * @param message log message
     */
    protected void log(String userId, String message) {
        if (userId == null)
            userId = "Unknown";
        Logger.info(userId + ": " + message);
    }
}
