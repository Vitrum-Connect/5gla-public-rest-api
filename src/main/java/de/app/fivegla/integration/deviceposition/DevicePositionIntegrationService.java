package de.app.fivegla.integration.deviceposition;

import de.app.fivegla.api.enums.EntityType;
import de.app.fivegla.persistence.ApplicationDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The DevicePositionIntegrationService class is responsible for integrating with external systems to create device positions.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DevicePositionIntegrationService {

    private final DevicePositionFiwareIntegrationServiceWrapper devicePositionFiwareIntegrationServiceWrapper;
    private final ApplicationDataRepository applicationDataRepository;

    /**
     * Creates a device position for the specified transaction, device, latitude, and longitude.
     *
     * @param deviceId      The ID of the device for which the position is being created.
     * @param transactionId The ID of the transaction related to the device position.
     * @param latitude      The latitude value of the device position.
     * @param longitude     The longitude value of the device position.
     */
    public void createDevicePosition(String tenantId,
                                     EntityType entityType,
                                     String deviceId,
                                     String transactionId,
                                     double latitude,
                                     double longitude) {
        log.info("Creating device position for device ID: {}, transaction ID: {}, latitude: {}, longitude: {}", deviceId, transactionId, latitude, longitude);
        var optionalTenant = applicationDataRepository.getTenant(tenantId);
        if (optionalTenant.isEmpty()) {
            log.error("Could not find tenant with ID: {}", tenantId);
        } else {
            var tenant = optionalTenant.get();
            devicePositionFiwareIntegrationServiceWrapper.persist(tenant,
                    entityType,
                    deviceId,
                    transactionId,
                    latitude,
                    longitude);
        }
    }

}
