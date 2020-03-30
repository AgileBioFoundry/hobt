package org.abf.hobt.lib;

import org.abf.hobt.lib.common.Log;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * Client for connecting to the biological part registry (ICE)
 *
 * @author Hector Plahar
 */
public class IceApiClient {

    private static final IceApiClient INSTANCE = new IceApiClient();
    private static final String API_KEY_TOKEN = "X-ICE-API-Token";
    private static final String API_KEY_CLIENT_ID = "X-ICE-API-Token-Client";
//    private static final String API_KEY_USER = "X-ICE-API-Token-User";
    private String url;
    private String apiClientToken;
    private String apiClientId;
    private Client client;

    private IceApiClient() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(MultiPartFeature.class);
        client = ClientBuilder.newClient(clientConfig);
        loadProperties();
    }

    public static IceApiClient getInstance() {
        return INSTANCE;
    }

    private void loadProperties() {
        Properties properties = new Properties();
        try {
            properties.load(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("config.properties")));
        } catch (Exception e) {
            Log.error(e);
        }
        url = properties.getProperty("ICE_URL");
        apiClientId = properties.getProperty("URI_PREFIX");
        apiClientToken = properties.getProperty("ICE_API_TOKEN");
    }

    private Invocation.Builder getInvocationHeaders(WebTarget target) {
        return target.request(MediaType.APPLICATION_JSON_TYPE)
                .header(API_KEY_TOKEN, apiClientToken)
                .header(API_KEY_CLIENT_ID, apiClientId);
    }

    public <T> T get(String path, Class<T> clazz, Map<String, String> parameters) {
        try {
            WebTarget target = client.target("https://" + url).path(path);
            if (parameters != null) {
                for (Map.Entry<String, String> entry : parameters.entrySet()) {
                    target = target.queryParam(entry.getKey(), entry.getValue());
                }
            }

            Invocation invocation = getInvocationHeaders(target).buildGet();
            return invocation.invoke(clazz);
        } catch (Exception e) {
            Log.error(e);
            return null;
        }
    }

    public <T> T post(String resourcePath, Object object, Class<T> responseClass) {
        WebTarget target = client.target("https://" + url).path(resourcePath);
        Invocation.Builder invocationBuilder = getInvocationHeaders(target);
        Response postResponse = invocationBuilder.post(Entity.entity(object, MediaType.APPLICATION_JSON_TYPE));
        if (postResponse.getStatus() == Response.Status.OK.getStatusCode() && postResponse.hasEntity())
            return postResponse.readEntity(responseClass);
        Log.error("Status from registry client post call " + postResponse.getStatus());
        return null;
    }

    public <T> T postWOR(Object object, Class<T> responseClass) {
        WebTarget target = client.target("https://www.webofregistries.org").path("rest/search");
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response postResponse = invocationBuilder.post(Entity.entity(object, MediaType.APPLICATION_JSON_TYPE));
        if (postResponse.getStatus() == Response.Status.OK.getStatusCode() && postResponse.hasEntity())
            return postResponse.readEntity(responseClass);
        Log.error("Status from  client post call " + postResponse.getStatus());
        return null;
    }
}
