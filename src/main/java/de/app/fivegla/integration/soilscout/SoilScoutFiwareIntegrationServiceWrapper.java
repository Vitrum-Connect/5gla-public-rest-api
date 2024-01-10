package de.app.fivegla.integration.soilscout;


import de.app.fivegla.api.FiwareDevicMeasurementeId;
import de.app.fivegla.api.FiwareDeviceId;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;
import de.app.fivegla.fiware.DeviceIntegrationService;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.model.Device;
import de.app.fivegla.fiware.model.DeviceCategory;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.Location;
import de.app.fivegla.integration.soilscout.model.Sensor;
import de.app.fivegla.integration.soilscout.model.SensorData;
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
public class SoilScoutFiwareIntegrationServiceWrapper {

    private final SoilScoutSensorIntegrationService soilScoutSensorIntegrationService;
    private final DeviceIntegrationService deviceIntegrationService;
    private final DeviceMeasurementIntegrationService deviceMeasurementIntegrationService;
    private final ApplicationConfiguration applicationConfiguration;

    /**
     * Create soil scout sensor data in FIWARE.
     *
     * @param sensorData the sensor data to create
     */
    public void persist(SensorData sensorData) {
        var soilScoutSensor = soilScoutSensorIntegrationService.fetch(sensorData.getDevice());
        log.debug("Found sensor with id {} in Soil Scout API.", sensorData.getDevice());
        persist(soilScoutSensor);

        var temperature = createDefaultDeviceMeasurement(sensorData, soilScoutSensor)
                .controlledProperty("temperature")
                .numValue(sensorData.getTemperature())
                .build();
        log.info("Persisting temperature measurement: {}", temperature);
        deviceMeasurementIntegrationService.persist(temperature);

        var moisture = createDefaultDeviceMeasurement(sensorData, soilScoutSensor)
                .controlledProperty("moisture")
                .numValue(sensorData.getMoisture())
                .build();
        log.info("Persisting moisture measurement: {}", moisture);
        deviceMeasurementIntegrationService.persist(moisture);

        var conductivity = createDefaultDeviceMeasurement(sensorData, soilScoutSensor)
                .controlledProperty("conductivity")
                .numValue(sensorData.getConductivity())
                .build();
        log.info("Persisting conductivity measurement: {}", conductivity);
        deviceMeasurementIntegrationService.persist(conductivity);

        var salinity = createDefaultDeviceMeasurement(sensorData, soilScoutSensor)
                .controlledProperty("salinity")
                .numValue(sensorData.getSalinity())
                .build();
        log.info("Persisting salinity measurement: {}", salinity);
        deviceMeasurementIntegrationService.persist(salinity);

        var waterBalance = createDefaultDeviceMeasurement(sensorData, soilScoutSensor)
                .controlledProperty("waterBalance")
                .numValue(sensorData.getWaterBalance())
                .build();
        log.info("Persisting waterBalance measurement: {}", waterBalance);
        deviceMeasurementIntegrationService.persist(waterBalance);
    }

    private void persist(Sensor sensor) {
        var device = Device.builder()
                .id(FiwareDeviceId.create(getManufacturerConfiguration(), String.valueOf(sensor.getId())))
                .deviceCategory(DeviceCategory.builder()
                        .value(List.of(getManufacturerConfiguration().getKey()))
                        .build())
                .build();
        deviceIntegrationService.persist(device);
    }

    private DeviceMeasurement.DeviceMeasurementBuilder createDefaultDeviceMeasurement(SensorData sensorData, Sensor sensor) {
        return DeviceMeasurement.builder()
                .id(FiwareDevicMeasurementeId.create(getManufacturerConfiguration()))
                .refDevice(FiwareDeviceId.create(getManufacturerConfiguration(), String.valueOf(sensorData.getDevice())))
                .dateObserved(sensorData.getTimestamp().toString())
                .location(Location.builder()
                        .coordinates(List.of(sensor.getLocation().getLatitude(), sensor.getLocation().getLongitude()))
                        .build());
    }

    private CommonManufacturerConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().soilscout();
    }

}
