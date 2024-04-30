package de.app.fivegla.integration.deviceposition;


import de.app.fivegla.api.enums.EntityType;
import de.app.fivegla.integration.fiware.FiwareEntityIntegrationService;
import de.app.fivegla.integration.fiware.model.DevicePosition;
import de.app.fivegla.integration.fiware.model.internal.DateTimeAttribute;
import de.app.fivegla.integration.fiware.model.internal.TextAttribute;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DevicePositionFiwareIntegrationServiceWrapper {

    private final FiwareEntityIntegrationService fiwareEntityIntegrationService;

    /**
     * Persists a device position for a specific tenant, measurement type, device ID, transaction ID, latitude, and longitude.
     *
     * @param tenant        The Tenant object representing the tenant.
     * @param entityType    The MeasurementType representing the type of measurement.
     * @param deviceId      The ID of the device for which the position is being persisted.
     * @param transactionId The ID of the transaction related to the device position.
     * @param latitude      The latitude value of the device position.
     * @param longitude     The longitude value of the device position.
     */
    public void persist(Tenant tenant,
                        EntityType entityType,
                        String deviceId,
                        String transactionId,
                        double latitude,
                        double longitude) {
        var devicePosition = new DevicePosition(
                tenant.getFiwarePrefix() + deviceId,
                entityType.getKey(),
                new TextAttribute(transactionId),
                new TextAttribute(deviceId),
                new DateTimeAttribute(Instant.now()),
                latitude,
                longitude
        );
        fiwareEntityIntegrationService.persist(devicePosition);
    }
}
