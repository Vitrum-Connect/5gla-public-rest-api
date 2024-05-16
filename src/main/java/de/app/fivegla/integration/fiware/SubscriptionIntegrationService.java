package de.app.fivegla.integration.fiware;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.enums.EntityType;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.fiware.api.CustomHeader;
import de.app.fivegla.integration.fiware.model.cygnus.*;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * SubscriptionService is a class that handles subscribing to a specific type in a context broker.
 * It extends the AbstractIntegrationService class and provides methods for creating a subscription and handling responses.
 *
 * @see AbstractIntegrationService
 */
@Slf4j
@SuppressWarnings("unused")
public class SubscriptionIntegrationService extends AbstractIntegrationService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final List<String> notificationUrls;

    public SubscriptionIntegrationService(String contextBrokerUrl, List<String> notificationUrls) {
        super(contextBrokerUrl);
        this.notificationUrls = notificationUrls;
    }

    /**
     * Creates or updates subscriptions for the specified types.
     *
     * @param entityTypes The types of entities to subscribe to.
     *                    Accepts multiple arguments of type String,
     *                    each representing a different type.
     */
    public void subscribe(Tenant tenant, EntityType... entityTypes) {
        var httpClient = HttpClient.newHttpClient();
        var subscriptions = createSubscriptions(entityTypes);
        for (var subscription : subscriptions) {
            String json = toJson(subscription);
            log.debug("Creating subscription: " + json);
            var httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(contextBrokerUrlForCommands() + "/subscriptions"))
                    .header("Content-Type", "application/json")
                    .header(CustomHeader.FIWARE_SERVICE, tenant.getTenantId())
                    .POST(HttpRequest.BodyPublishers.ofString(json)).build();
            try {
                var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() != 201) {
                    log.error("Could not create subscription. Response: {}", response.body());
                    log.debug("Request: {}", json);
                    log.debug("Response: {}", response.body());
                    throw new BusinessException(ErrorMessage.builder()
                            .message("Could not create subscription, there was an error from FIWARE.")
                            .error(Error.FIWARE_INTEGRATION_LAYER_ERROR)
                            .build());
                } else {
                    log.info("Subscription created/updated successfully.");
                }
            } catch (Exception e) {
                log.error("Could not create subscription.", e);
                throw new BusinessException(ErrorMessage.builder()
                        .message("Could not create subscription.")
                        .error(Error.FIWARE_INTEGRATION_LAYER_ERROR)
                        .build());
            }
        }
    }

    private List<Subscription> createSubscriptions(EntityType... entityTypes) {
        log.debug("Creating subscriptions for entityTypes: " + Arrays.toString(entityTypes));
        var subscriptions = new ArrayList<Subscription>();
        for (var notificationUrl : notificationUrls) {
            var subscription = Subscription.builder()
                    .description("Subscription for " + Arrays.toString(entityTypes) + " type")
                    .subject(Subject.builder()
                            .entities(createSubscriptionEntities(entityTypes))
                            .build())
                    .notification(Notification.builder()
                            .http(Http.builder()
                                    .url(notificationUrl)
                                    .build())
                            .httpCustom(HttpCustom.builder()
                                    .url(notificationUrl)
                                    .build())
                            .build())
                    .build();
            subscriptions.add(subscription);
        }
        return subscriptions;
    }

    private List<Entity> createSubscriptionEntities(EntityType... entityTypes) {
        var entities = new ArrayList<Entity>();
        for (var type : entityTypes) {
            entities.add(Entity.builder()
                    .idPattern(".*")
                    .type(type.getKey())
                    .build());
        }
        return entities;
    }

    /**
     * Removes all subscriptions of the specified type.
     *
     * @param tenant the tenant to remove subscriptions for
     * @param type   the type of subscriptions to be removed
     */
    public void removeAll(Tenant tenant, EntityType type) {
        findAll(tenant, type).forEach(subscription -> removeSubscription(tenant, subscription));
    }

    private void removeSubscription(Tenant tenant, Subscription subscription) {
        var httpClient = HttpClient.newHttpClient();
        var httpRequest = HttpRequest.newBuilder()
                .header(CustomHeader.FIWARE_SERVICE, tenant.getTenantId())
                .uri(URI.create(contextBrokerUrlForCommands() + "/subscriptions/" + subscription.getId()))
                .DELETE().build();
        try {
            var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 204) {
                log.error("Could not remove subscription. Response: {}", response.body());
                log.debug("Response: {}", response.body());
                throw new BusinessException(ErrorMessage.builder()
                        .message("Could not remove subscription, there was an error from FIWARE.")
                        .error(Error.FIWARE_INTEGRATION_LAYER_ERROR)
                        .build());
            } else {
                log.info("Subscription removed successfully.");
            }
        } catch (Exception e) {
            log.error("Could not remove subscription.", e);
            throw new BusinessException(ErrorMessage.builder()
                    .message("Could not remove subscription.")
                    .error(Error.FIWARE_INTEGRATION_LAYER_ERROR)
                    .build());
        }
    }

    /**
     * Finds all subscriptions of a given entityType.
     *
     * @param tenant     The tenant to find subscriptions for.
     * @param entityType The entityType of subscription to find.
     * @return A list of Subscription objects matching the given entityType.
     */
    public List<Subscription> findAll(Tenant tenant, EntityType entityType) {
        var httpClient = HttpClient.newHttpClient();
        var httpRequest = HttpRequest.newBuilder()
                .header(CustomHeader.FIWARE_SERVICE, tenant.getTenantId())
                .uri(URI.create(contextBrokerUrlForCommands() + "/subscriptions"))
                .GET().build();
        try {
            var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                log.error("Could not find subscriptions. Response: {}", response.body());
                log.debug("Response: {}", response.body());
                throw new BusinessException(ErrorMessage.builder()
                        .message("Could not find subscriptions, there was an error from FIWARE.")
                        .error(Error.FIWARE_INTEGRATION_LAYER_ERROR)
                        .build());
            } else {
                log.info("Subscription found successfully.");
                return toListOfObjects(response.body()).stream().filter(subscription -> subscription.getSubject().getEntities()
                        .stream()
                        .anyMatch(entity -> entity.getType().equals(entityType.getKey()))).toList();
            }
        } catch (Exception e) {
            log.error("Could not find subscriptions.", e);
            throw new BusinessException(ErrorMessage.builder()
                    .message("Could not find subscriptions.")
                    .error(Error.FIWARE_INTEGRATION_LAYER_ERROR)
                    .build());
        }
    }

    /**
     * Converts the current object to a JSON string representation.
     *
     * @param object the object to convert
     * @return a JSON string representing the current object
     */
    private String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writer().withDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Could not transform object to JSON.", e);
            throw new BusinessException(ErrorMessage.builder()
                    .message("Could not find subscriptions.")
                    .error(Error.FIWARE_INTEGRATION_LAYER_ERROR)
                    .build());
        }
    }

    private List<Subscription> toListOfObjects(String json) {
        try {
            var type = OBJECT_MAPPER.getTypeFactory()
                    .constructCollectionType(List.class, Subscription.class);
            return OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            log.error("Could not transform object to JSON.", e);
            throw new BusinessException(ErrorMessage.builder()
                    .message("Could not transform JSON to object.")
                    .error(Error.FIWARE_INTEGRATION_LAYER_ERROR)
                    .build());
        }
    }

}
