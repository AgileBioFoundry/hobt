package org.abf.hobt.service.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.abf.hobt.account.AccountAuthorization;
import org.abf.hobt.account.AccountRoles;
import org.abf.hobt.account.Accounts;
import org.abf.hobt.account.Users;
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
    public Response create(@DefaultValue("true") @QueryParam("sendEmail") boolean sendEmail, Account account) {
        Logger.info("Creating new account");
        try {
            Accounts accounts = new Accounts();
            account = accounts.create(account, sendEmail);
        } catch (IllegalArgumentException e) {
            Logger.error("Duplicated user id: " + account.getUserId(), e);
            return super.respond(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return super.respond(account);
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

//    @POST
//    @Path("{id}/roles")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response addRole(@PathParam("id") long id, AccountRole role) {
//        String userId = getUserId(true);
//        Logger.info(userId + ": adding role " + role + " to account " + id);
//        Accounts accounts = new Accounts();
//        boolean success = false; //accounts.addRole(id, role);
//        return respond(success);
//    }

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
    @Path("{id}/roles/{roleId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeRole(@PathParam("id") long id,
                               @PathParam("roleId") long roleId) {
        String userId = getUserId(true);
        Logger.info(userId + ": removing role " + roleId + " from account " + id);
        AccountRoles roles = new AccountRoles(userId);
        roles.removeRole(id, roleId);
        return respond(true);
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

    @PUT
    @Path("{id}/active")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addToActiveUsers(@PathParam("id") long id) {
        String userId = getUserId(true);
        Accounts accounts = new Accounts(userId);
        return super.respond(accounts.setDisabled(id, false));
    }

    @DELETE
    @Path("{id}/active")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeFromActiveUsers(@PathParam("id") long id) {
        String userId = getUserId(true);
        Accounts accounts = new Accounts(userId);
        return super.respond(accounts.setDisabled(id, true));
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteAccount(@PathParam("id") long id) {
        String userId = getUserId(true);
        Accounts accounts = new Accounts(userId);
        return super.respond(accounts.delete(id));
    }
}
