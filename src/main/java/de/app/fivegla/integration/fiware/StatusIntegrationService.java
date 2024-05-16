package de.app.fivegla.integration.fiware;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.fiware.model.Version;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@SuppressWarnings("unused")
public class StatusIntegrationService extends AbstractIntegrationService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public StatusIntegrationService(String contextBrokerUrl) {
        super(contextBrokerUrl);
    }

    /**
     * Retrieves the version of the context broker.
     * Makes an HTTP GET request to the specified context broker URL and
     * parses the response to extract the version information.
     *
     * @return the version of the context broker
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
                log.error("Could not fetch version. Response: {}", response.body());
                log.debug("Response: {}", response.body());
                throw new BusinessException(ErrorMessage.builder()
                        .message("Could not fetch version, there was an error from FIWARE.")
                        .error(Error.FIWARE_INTEGRATION_LAYER_ERROR)
                        .build());
            } else {
                log.info("Subscription created/updated successfully.");
                return toObject(response.body());
            }
        } catch (Exception e) {
            log.error("Could not fetch version.", e);
            throw new BusinessException(ErrorMessage.builder()
                    .message("Could not fetch version from FIWARE.")
                    .error(Error.FIWARE_INTEGRATION_LAYER_ERROR)
                    .build());
        }
    }

    private Version toObject(String json) {
        try {
            var type = OBJECT_MAPPER.getTypeFactory()
                    .constructType(Version.class);
            return OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            log.error("Could not transform JSON to object.", e);
            throw new BusinessException(ErrorMessage.builder()
                    .message("Could not transform JSON to object.")
                    .error(Error.FIWARE_INTEGRATION_LAYER_ERROR)
                    .build());
        }
    }
}
