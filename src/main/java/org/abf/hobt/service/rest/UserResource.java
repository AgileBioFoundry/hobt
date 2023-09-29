package org.abf.hobt.service.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.abf.hobt.account.*;
import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.dto.Account;
import org.abf.hobt.dto.Role;

/**
 * REST user resource
 *
 * @author Hector Plahar
 */
@Path("/users")
public class UserResource extends RestResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Account accountTransfer) {
        Logger.info("Creating new account");
        try {
            Accounts accounts = new Accounts();
            accountTransfer = accounts.create(accountTransfer, true);
        } catch (IllegalArgumentException e) {
            Logger.error(e);
            return super.respond(Response.Status.CONFLICT);
        }
        return super.respond(accountTransfer);
    }

    /**
     * Retrieves (up to specified limit), the list of users that match the value
     *
     * @param val   text to match against users
     * @param limit upper limit for number of users to return
     * @return list of matching users
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/autocomplete")
    public Response getAutoCompleteForAvailableAccounts(@QueryParam("val") String val,
                                                        @DefaultValue("8") @QueryParam("limit") int limit) {
//        String userId = getUserId();
        Users users = new Users();
        return super.respond(users.filter(val, limit));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getList(@DefaultValue("15") @QueryParam("limit") int limit,
                            @DefaultValue("0") @QueryParam("start") int start,
                            @DefaultValue("false") @QueryParam("asc") boolean asc,
                            @DefaultValue("id") @QueryParam("sort") String sort,
                            @QueryParam("filterText") String filter) {
        String userId = getUserId(true);
        new AccountAuthorization().expectAdmin(userId);
        Logger.info(userId + ": retrieving all accounts");
        Accounts accounts = new Accounts();
        return super.respond(accounts.retrieveAccounts(userId, start, limit, asc, sort, filter));
    }

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("roles/{type}")
//    public Response getAccountsByRole(@PathParam("type") AccountRole role) {
//        String userId = getUserIdFromSessionHeader(sessionId);
//        Logger.info(userId + ": retrieving accounts by role " + role);
//        Accounts accounts = new Accounts();
//        List<Account> result = accounts.getAccountsByRole(userId, role);
//        return respond(result);
//    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response get(@PathParam("id") String id) {
        String userId = getUserId(true);
        Logger.info(userId + ": retrieving information for user '" + id + "'");
        Accounts accounts = new Accounts();
        Account result = accounts.get(userId, id);
        return super.respond(result);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response update(@PathParam("id") long id,
                           Account transfer) {
        String userId = getUserId(true);
        Logger.info(userId + ": updating account " + id);
        Accounts accounts = new Accounts();
        boolean success = accounts.update(userId, id, transfer);
        return super.respond(success);
    }

    @POST
    @Path("{id}/roles")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addRole(@PathParam("id") long id, AccountRole role) {
        String userId = getUserId(true);
        Logger.info(userId + ": adding role " + role + " to account " + id);
        Accounts accounts = new Accounts();
        boolean success = false; //accounts.addRole(id, role);
        return respond(success);
    }

    @PUT
    @Path("{id}/roles")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addRole(@PathParam("id") long id, Role role) {
        String userId = getUserId(true);
        Logger.info(userId + ": adding role with id " + role.getId() + " to account " + id);
        AccountRoles roles = new AccountRoles(userId);
        roles.addRole(id, role.getId());
        return respond(true);
    }

    @DELETE
    @Path("{id}/roles/{role}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeRole(@PathParam("id") long id,
                               @PathParam("role") AccountRole role) {
        String userId = getUserId(true);
        Logger.info(userId + ": removing role " + role + " from account " + id);
        Accounts accounts = new Accounts();
//        boolean success = accounts.removeRole(id, role);
        return respond(false);
    }

    @GET
    @Path("{id}/permissions")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPermissions(@PathParam("id") long id) {
        String userId = getUserId(true);
        Logger.info(userId + ": retrieving permissions for " + id);
        AccountRoles roles = new AccountRoles(userId);
        return super.respond(roles.getPermissions(id));
    }

}
