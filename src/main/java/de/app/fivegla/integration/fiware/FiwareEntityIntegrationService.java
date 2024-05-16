package de.app.fivegla.integration.fiware;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.fiware.api.CustomHeader;
import de.app.fivegla.integration.fiware.api.FiwareEntityChecker;
import de.app.fivegla.integration.fiware.model.api.FiwareEntity;
import de.app.fivegla.integration.fiware.request.UpdateOrCreateFiwareEntitiesRequest;
import de.app.fivegla.persistence.entity.Group;
import de.app.fivegla.persistence.entity.Tenant;
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
public class FiwareEntityIntegrationService extends AbstractIntegrationService {

    public FiwareEntityIntegrationService(String contextBrokerUrl) {
        super(contextBrokerUrl);
    }

    /**
     * Creates a new device in the context broker.
     *
     * @param entity the device to create
     */
    public void persist(Tenant tenant, Group group, FiwareEntity entity) {
        FiwareEntityChecker.check(entity);
        var updateOrCreateEntityRequest = UpdateOrCreateFiwareEntitiesRequest.builder()
                .entities(List.of(entity))
                .build();
        var httpClient = HttpClient.newHttpClient();
        var requestBody = HttpRequest.BodyPublishers.ofString(updateOrCreateEntityRequest.asJson());
        log.debug("Request: {}", updateOrCreateEntityRequest.asJson());
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(contextBrokerUrlForCommands() + "/op/update"))
                .header("Content-Type", "application/json")
                .header(CustomHeader.FIWARE_SERVICE, tenant.getTenantId())
                .header(CustomHeader.FIWARE_SERVICE_PATH, "/" + group.getName())
                .POST(requestBody).build();
        try {
            var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 204) {
                log.error("Could not create entity. Response: {}", response.body());
                log.debug("Request: {}", updateOrCreateEntityRequest.asJson());
                log.debug("Response: {}", response.body());
                throw new BusinessException(ErrorMessage.builder()
                        .message("Could not create entity, there was an error from FIWARE.")
                        .error(Error.FIWARE_INTEGRATION_LAYER_ERROR)
                        .build());

            } else {
                log.info("Device created/updated successfully.");
            }
        } catch (Exception e) {
            log.error("Could not create entity.", e);
            throw new BusinessException(ErrorMessage.builder()
                    .message("Could not create entity in FIWARE.")
                    .error(Error.FIWARE_INTEGRATION_LAYER_ERROR)
                    .build());
        }
    }

}
