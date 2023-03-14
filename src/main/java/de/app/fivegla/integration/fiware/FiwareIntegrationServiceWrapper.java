package de.app.fivegla.integration.fiware;


import de.app.fivegla.api.Constants;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.fiware.DeviceIntegrationService;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.api.enums.DeviceCategoryValues;
import de.app.fivegla.fiware.model.Device;
import de.app.fivegla.fiware.model.DeviceCategory;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.Location;
import de.app.fivegla.integration.soilscout.SoilScoutSensorIntegrationService;
import de.app.fivegla.integration.soilscout.model.SoilScoutSensor;
import de.app.fivegla.integration.soilscout.model.SoilScoutSensorData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
public class FiwareIntegrationServiceWrapper {

    private final SoilScoutSensorIntegrationService soilScoutSensorIntegrationService;
    private final DeviceIntegrationService deviceIntegrationService;
    private final DeviceMeasurementIntegrationService deviceMeasurementIntegrationService;

    public FiwareIntegrationServiceWrapper(SoilScoutSensorIntegrationService soilScoutSensorIntegrationService,
                                           DeviceIntegrationService deviceIntegrationService,
                                           DeviceMeasurementIntegrationService deviceMeasurementIntegrationService) {
        this.soilScoutSensorIntegrationService = soilScoutSensorIntegrationService;
        this.deviceIntegrationService = deviceIntegrationService;
        this.deviceMeasurementIntegrationService = deviceMeasurementIntegrationService;
    }

    /**
     * Create a new soil scout sensor in FIWARE.
     *
     * @param sensor the sensor to create
     */
    public void persist(SoilScoutSensor sensor) {
        var device = Device.builder()
                .id(getFiwareId(sensor.getId()))
                .deviceCategory(DeviceCategory.builder()
                        .value(List.of(DeviceCategoryValues.SoilScoutSensor.getKey()))
                        .build())
                .build();
        deviceIntegrationService.persist(device);
    }

    /**
     * Create soil scout sensor data in FIWARE.
     *
     * @param sensorData the sensor data to create
     */
    public void persist(SoilScoutSensorData sensorData) {
        try {
            var soilScoutSensor = soilScoutSensorIntegrationService.find(sensorData.getDevice());
            log.debug("Found sensor with id {} in Soil Scout API.", sensorData.getDevice());

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

        } catch (BusinessException e) {
            log.error("Could not find sensor with id {} in Soil Scout API.", sensorData.getDevice());
        }
    }

    private static DeviceMeasurement.DeviceMeasurementBuilder createDefaultDeviceMeasurement(SoilScoutSensorData sensorData, SoilScoutSensor soilScoutSensor) {
        return DeviceMeasurement.builder()
                .id(getFiwareId())
                .refDevice(getFiwareId(sensorData.getDevice()))
                .dateObserved(sensorData.getTimestamp().toString())
                .location(Location.builder()
                        .coordinates(List.of(soilScoutSensor.getLocation().getLatitude(), soilScoutSensor.getLocation().getLongitude()))
                        .build());
    }

    private static String getFiwareId(int sensorId) {
        return Constants.FIWARE_SOIL_SCOUT_SENSOR_ID_PREFIX + sensorId;
    }

    private static String getFiwareId() {
        return Constants.FIWARE_SOIL_SCOUT_SENSOR_ID_PREFIX + UUID.randomUUID();
    }

}
