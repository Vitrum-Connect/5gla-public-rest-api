package de.app.fivegla.fiware;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.app.fivegla.fiware.api.FiwareIntegrationLayerException;
import de.app.fivegla.fiware.model.Version;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@SuppressWarnings("unused")
public class StatusService extends AbstractIntegrationService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public StatusService(String contextBrokerUrl, String tenant) {
        super(contextBrokerUrl, tenant);
    }

    /**
     * Retrieves the version of the context broker.
     * Makes an HTTP GET request to the specified context broker URL and
     * parses the response to extract the version information.
     *
     * @return the version of the context broker
     * @throws FiwareIntegrationLayerException if there was an error fetching the version
     */
    public Version getVersion() {
        var httpClient = HttpClient.newHttpClient();
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(contextBrokerUrl() + "/version"))
                .header("Accept", "application/json")
                .GET().build();
        try {
            var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                log.error("Could not fetch version. Response: " + response.body());
                log.debug("Response: " + response.body());
                throw new FiwareIntegrationLayerException("Could not fetch version, there was an error from FIWARE.");
            } else {
                log.info("Subscription created/updated successfully.");
                return toObject(response.body());
            }
        } catch (Exception e) {
            throw new FiwareIntegrationLayerException("Could not fetch version from FIWARE.", e);
        }
    }

    private Version toObject(String json) {
        try {
            var type = OBJECT_MAPPER.getTypeFactory()
                    .constructType(Version.class);
            return OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new FiwareIntegrationLayerException("Could not transform JSON to object.", e);
        }
    }
}
