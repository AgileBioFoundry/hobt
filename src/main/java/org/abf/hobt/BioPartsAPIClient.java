package org.abf.hobt;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.service.rest.ArrayDataJSONHandler;
import org.abf.hobt.service.rest.DataTransferObjectJSONHandler;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

public class BioPartsAPIClient {
    private static final BioPartsAPIClient INSTANCE = new BioPartsAPIClient();
    private final Client client;
    private final String URL = "bioparts.org";

    private BioPartsAPIClient() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(DataTransferObjectJSONHandler.class);
        clientConfig.register(MultiPartFeature.class);
        clientConfig.register(ArrayDataJSONHandler.class);
        client = ClientBuilder.newClient(clientConfig);
//        loadProperties();
    }

    private Invocation.Builder getInvocationHeaders(WebTarget target) {
        return target.request(MediaType.APPLICATION_JSON_TYPE);
    }

    public static BioPartsAPIClient getInstance() {
        return INSTANCE;
    }

    public <T> T post(String resourcePath, Object object, Class<T> responseClass) {
        WebTarget target = client.target("https://" + URL).path(resourcePath);
        Invocation.Builder invocationBuilder = getInvocationHeaders(target);
        try (Response postResponse = invocationBuilder.post(Entity.entity(object, MediaType.APPLICATION_JSON_TYPE))) {
            if (postResponse.getStatus() == Response.Status.OK.getStatusCode() && postResponse.hasEntity())
                return postResponse.readEntity(responseClass);
            Logger.error("Status from registry client post call " + postResponse.getStatus());
        }
        return null;
    }
}
