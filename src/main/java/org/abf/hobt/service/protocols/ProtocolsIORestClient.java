package org.abf.hobt.service.protocols;

import org.abf.hobt.common.ResultData;
import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.config.ConfigurationValue;
import org.abf.hobt.config.Settings;
import org.abf.hobt.dto.Protocol;
import org.abf.hobt.service.rest.ArrayDataJSONHandler;
import org.abf.hobt.service.rest.DataTransferObjectJSONHandler;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * REST client for interacting with the protocols.io API
 *
 * @author Hector Plahar
 */
public class ProtocolsIORestClient {

    private static final ProtocolsIORestClient INSTANCE = new ProtocolsIORestClient();
    private final Client client;
    private static final String url = "https://protocols.io/api/v3/protocols";   // todo make config
    private final String API_KEY_TOKEN;

    private ProtocolsIORestClient() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(DataTransferObjectJSONHandler.class);
        clientConfig.register(MultiPartFeature.class);
        clientConfig.register(ArrayDataJSONHandler.class);
        clientConfig.register(ProtocolsResponse.class);
        client = ClientBuilder.newClient(clientConfig);
        API_KEY_TOKEN = new Settings().getValue(ConfigurationValue.PROTOCOL_IO_API_TOKEN);
    }

    public static ProtocolsIORestClient getInstance() {
        return INSTANCE;
    }

    private Invocation.Builder getInvocationHeaders(WebTarget target) {
        return target.request(MediaType.APPLICATION_JSON_TYPE)
            .header("Authorization", "Bearer " + API_KEY_TOKEN);
    }

    public ResultData<Protocol> get(Map<String, String> parameters) {
        try {
            WebTarget target = client.target(url); //.path(path);
            if (parameters != null) {
                for (Map.Entry<String, String> entry : parameters.entrySet()) {
                    target = target.queryParam(entry.getKey(), entry.getValue());
                }
            }

            Invocation invocation = getInvocationHeaders(target).buildGet();
            ProtocolsResponse protocolsResponse;
            try (Response response = invocation.invoke()) {
                // todo : expect a response status of 200
                protocolsResponse = response.readEntity(ProtocolsResponse.class);
            }

            if (protocolsResponse == null)
                return null;

            // status code of 0 is success
            if (protocolsResponse.getStatus_code() != 0)
                return null;

            ResultData<Protocol> results = new ResultData<>();
            results.setAvailable(protocolsResponse.getTotal());
            results.getRequested().addAll(protocolsResponse.getItems());
            return results;
        } catch (Exception e) {
            Logger.error(e);
            return null;
        }
    }
}
