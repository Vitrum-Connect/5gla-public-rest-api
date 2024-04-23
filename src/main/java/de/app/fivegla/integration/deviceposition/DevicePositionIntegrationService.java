package de.app.fivegla.integration.deviceposition;

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

    /**
     * Creates a device position for the specified transaction, device, latitude, and longitude.
     *
     * @param transactionId The ID of the transaction related to the device position.
     * @param deviceId      The ID of the device for which the position is being created.
     * @param latitude      The latitude value of the device position.
     * @param longitude     The longitude value of the device position.
     */
    public void createDevicePosition(String transactionId, String deviceId, double latitude, double longitude) {
        log.info("Creating device position for device {} with transaction id {} at latitude {} and longitude {}.", deviceId, transactionId, latitude, longitude);
    }

}
