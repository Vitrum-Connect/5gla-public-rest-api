package de.app.fivegla.business;

import de.app.fivegla.api.enums.EntityType;
import de.app.fivegla.integration.deviceposition.DevicePositionFiwareIntegrationServiceWrapper;
import de.app.fivegla.persistence.entity.Group;
import de.app.fivegla.persistence.entity.Tenant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The DevicePositionIntegrationService class is responsible for integrating with external systems to create device positions.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DevicePositionService {

    private final DevicePositionFiwareIntegrationServiceWrapper devicePositionFiwareIntegrationServiceWrapper;

    /**
     * Creates a device position for the specified transaction, device, latitude, and longitude.
     *
     * @param deviceId      The ID of the device for which the position is being created.
     * @param transactionId The ID of the transaction related to the device position.
     * @param latitude      The latitude value of the device position.
     * @param longitude     The longitude value of the device position.
     */
    @Transactional
    public void createDevicePosition(Tenant tenant,
                                     Group group,
                                     EntityType entityType,
                                     String deviceId,
                                     String transactionId,
                                     double latitude,
                                     double longitude) {
        log.info("Creating device position for device ID: {}, transaction ID: {}, latitude: {}, longitude: {}", deviceId, transactionId, latitude, longitude);
        devicePositionFiwareIntegrationServiceWrapper.persist(tenant,
                entityType,
                group,
                deviceId,
                transactionId,
                latitude,
                longitude);
    }

}
