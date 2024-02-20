package de.app.fivegla.integration.agranimo;


import de.app.fivegla.api.FiwareDeviceId;
import de.app.fivegla.api.Format;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.Location;
import de.app.fivegla.integration.agranimo.model.SoilMoisture;
import de.app.fivegla.integration.agranimo.model.Zone;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class AgranimoFiwareIntegrationServiceWrapper {
    private final ApplicationConfiguration applicationConfiguration;
    private final DeviceMeasurementIntegrationService deviceMeasurementIntegrationService;

    /**
     * Persists the soil moisture measurement for a given zone.
     *
     * @param zone         the zone associated with the soil moisture measurement
     * @param soilMoisture the soil moisture measurement to persist
     */
    public void persist(Zone zone, SoilMoisture soilMoisture) {
        var deviceMeasurement = createDeviceMeasurements(zone, soilMoisture);
        log.info("Persisting measurement for device: {}", soilMoisture.getDeviceId());
        deviceMeasurementIntegrationService.persist(deviceMeasurement);
    }

    private DeviceMeasurement createDeviceMeasurements(Zone zone, SoilMoisture soilMoisture) {
        log.debug("Persisting data for zone: {}", zone.getId());
        return DeviceMeasurement.builder()
                .id(deviceIdOf(soilMoisture.getDeviceId()))
                .dateObserved(Format.format(soilMoisture.getTms()))
                .location(Location.builder()
                        .coordinates(List.of(zone.getData().getPoint().getCoordinates()[0], zone.getData().getPoint().getCoordinates()[1]))
                        .build())
                .controlledProperty("smo1")
                .numValue(soilMoisture.getSmo1())
                .controlledProperty("smo2")
                .numValue(soilMoisture.getSmo2())
                .controlledProperty("smo3")
                .numValue(soilMoisture.getSmo3())
                .controlledProperty("smo4")
                .numValue(soilMoisture.getSmo4())
                .build();
    }

    private CommonManufacturerConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().agranimo();
    }

    /**
     * Retrieves the unique device ID of the specified device.
     *
     * @param deviceId The unique identifier of the device.
     * @return The device ID.
     */
    public String deviceIdOf(String deviceId) {
        return FiwareDeviceId.create(getManufacturerConfiguration(), deviceId);
    }
}
