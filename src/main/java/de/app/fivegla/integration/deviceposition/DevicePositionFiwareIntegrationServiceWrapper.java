package de.app.fivegla.integration.deviceposition;


import de.app.fivegla.api.enums.MeasurementType;
import de.app.fivegla.fiware.DevicePositionIntegrationService;
import de.app.fivegla.fiware.model.DevicePosition;
import de.app.fivegla.fiware.model.internal.EmptyAttribute;
import de.app.fivegla.fiware.model.internal.TextAttribute;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DevicePositionFiwareIntegrationServiceWrapper {

    @Qualifier("fiwareDevicePositionIntegrationService")
    private final DevicePositionIntegrationService devicePositionIntegrationService;

    /**
     * Persists a device position for a specific tenant, measurement type, device ID, transaction ID, latitude, and longitude.
     *
     * @param tenant          The Tenant object representing the tenant.
     * @param measurementType The MeasurementType representing the type of measurement.
     * @param deviceId        The ID of the device for which the position is being persisted.
     * @param transactionId   The ID of the transaction related to the device position.
     * @param latitude        The latitude value of the device position.
     * @param longitude       The longitude value of the device position.
     */
    public void persist(Tenant tenant, MeasurementType measurementType, String deviceId, String transactionId, double latitude, double longitude) {
        var devicePosition = new DevicePosition(
                tenant.getFiwarePrefix() + deviceId,
                measurementType.name(),
                new TextAttribute(transactionId),
                new TextAttribute(deviceId),
                new EmptyAttribute(),
                latitude,
                longitude
        );
        devicePositionIntegrationService.persist(devicePosition);
    }
}
