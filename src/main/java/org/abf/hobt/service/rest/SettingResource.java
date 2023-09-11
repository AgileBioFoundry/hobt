package org.abf.hobt.service.rest;

import org.abf.hobt.account.AccountAuthorization;
import org.abf.hobt.common.ResultData;
import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.common.util.StringUtils;
import org.abf.hobt.config.ConfigurationValue;
import org.abf.hobt.config.Settings;
import org.abf.hobt.dto.Setting;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * REST API for configuring the application
 *
 * @author Hector Plahar
 */
@Path("/settings")
public class SettingResource extends RestResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/register")
    public Response getSetting() {
        Settings settings = new Settings();
        return respond(settings.get(ConfigurationValue.REGISTRATION_ALLOWED));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getSettings() {
        String userId = getUserId(true);
        AccountAuthorization authorization = new AccountAuthorization();
        authorization.expectAdmin(userId);

        Logger.info(userId + ": retrieving system settings");
        ResultData<Setting> result = new ResultData<>();
        Settings settings = new Settings();
        result.getRequested().addAll(settings.getAll());
        return respond(result);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@Context UriInfo uriInfo, Setting setting) {
        String userId = getUserId(true);
        AccountAuthorization authorization = new AccountAuthorization();
        authorization.expectAdmin(userId); // todo : might have to filter out some settings

        if (StringUtils.isBlank(setting.getKey()))
            throw new IllegalArgumentException("Cannot create setting with empty key");
        Logger.info(userId + ": updating setting " + setting);
        Settings settings = new Settings();
        return super.respond(settings.update(setting));
    }

    @GET
    @Path("/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSetting(@PathParam("key") String key) {
        getUserId(true);
        ConfigurationValue valueKey = ConfigurationValue.valueOf(key.toUpperCase());
        Settings settings = new Settings();
        return super.respond(settings.get(valueKey));
    }
}
