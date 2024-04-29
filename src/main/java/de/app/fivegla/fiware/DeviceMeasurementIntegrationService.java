package de.app.fivegla.fiware;

import de.app.fivegla.fiware.api.CustomHeader;
import de.app.fivegla.fiware.api.FiwareIntegrationLayerException;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.request.UpdateOrCreateDeviceMeasurementRequest;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * Integration service for FIWARE to send requests to the context broker.
 */
@Slf4j
public class DeviceMeasurementIntegrationService extends AbstractIntegrationService {

    public DeviceMeasurementIntegrationService(String contextBrokerUrl, String tenant) {
        super(contextBrokerUrl, tenant);
    }

    /**
     * Creates a new device in the context broker.
     *
     * @param entity the device to create
     */
    public void persist(DeviceMeasurement entity) {
        var updateOrCreateEntityRequest = UpdateOrCreateDeviceMeasurementRequest.builder()
                .entities(List.of(entity))
                .build();
        var httpClient = HttpClient.newHttpClient();
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(contextBrokerUrlForCommands() + "/op/update"))
                .header("Content-Type", "application/json")
                .header(CustomHeader.FIWARE_SERVICE, getTenant())
                .POST(HttpRequest.BodyPublishers.ofString(updateOrCreateEntityRequest.asJson())).build();
        try {
            var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 204) {
                log.error("Could not create entity. Response: " + response.body());
                log.debug("Request: " + updateOrCreateEntityRequest.asJson());
                log.debug("Response: " + response.body());
                throw new FiwareIntegrationLayerException("Could not create entity, there was an error from FIWARE.");
            } else {
                log.info("Device created/updated successfully.");
            }
        } catch (Exception e) {
            throw new FiwareIntegrationLayerException("Could not create/update entity", e);
        }
    }

}
