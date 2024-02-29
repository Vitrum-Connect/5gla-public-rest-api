package de.app.fivegla.integration.soilscout;


import de.app.fivegla.api.enums.MeasurementType;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.api.FiwareTypes;
import de.app.fivegla.fiware.model.builder.DeviceMeasurementBuilder;
import de.app.fivegla.integration.soilscout.model.Sensor;
import de.app.fivegla.integration.soilscout.model.SensorData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SoilScoutFiwareIntegrationServiceWrapper {

    private final SoilScoutSensorIntegrationService soilScoutSensorIntegrationService;
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

        var temperature = defaultMeasurement(soilScoutSensor)
                .withMeasurement("temperature",
                        FiwareTypes.TEXT.getKey(),
                        String.valueOf(sensorData.getTemperature()),
                        sensorData.getTimestamp().toInstant(),
                        new DeviceMeasurementBuilder.MetadataEntry("controlledProperty",
                                FiwareTypes.TEXT.getKey(),
                                "temperature"))
                .build();
        log.info("Persisting temperature measurement: {}", temperature);
        deviceMeasurementIntegrationService.persist(temperature);

        var moisture = defaultMeasurement(soilScoutSensor)
                .withMeasurement("moisture",
                        FiwareTypes.TEXT.getKey(),
                        String.valueOf(sensorData.getMoisture()),
                        sensorData.getTimestamp().toInstant(),
                        new DeviceMeasurementBuilder.MetadataEntry("controlledProperty",
                                FiwareTypes.TEXT.getKey(),
                                "moisture"))
                .build();
        log.info("Persisting moisture measurement: {}", moisture);
        deviceMeasurementIntegrationService.persist(moisture);

        var conductivity = defaultMeasurement(soilScoutSensor)
                .withMeasurement("conductivity",
                        FiwareTypes.TEXT.getKey(),
                        String.valueOf(sensorData.getConductivity()),
                        sensorData.getTimestamp().toInstant(),
                        new DeviceMeasurementBuilder.MetadataEntry("controlledProperty",
                                FiwareTypes.TEXT.getKey(),
                                "conductivity"))
                .build();
        log.info("Persisting conductivity measurement: {}", conductivity);
        deviceMeasurementIntegrationService.persist(conductivity);

        var salinity = defaultMeasurement(soilScoutSensor)
                .withMeasurement("salinity",
                        FiwareTypes.TEXT.getKey(),
                        String.valueOf(sensorData.getSalinity()),
                        sensorData.getTimestamp().toInstant(),
                        new DeviceMeasurementBuilder.MetadataEntry("controlledProperty",
                                FiwareTypes.TEXT.getKey(),
                                "salinity"))
                .build();
        log.info("Persisting salinity measurement: {}", salinity);
        deviceMeasurementIntegrationService.persist(salinity);

        var waterBalance = defaultMeasurement(soilScoutSensor)
                .withMeasurement("waterBalance",
                        FiwareTypes.TEXT.getKey(),
                        String.valueOf(sensorData.getWaterBalance()),
                        sensorData.getTimestamp().toInstant(),
                        new DeviceMeasurementBuilder.MetadataEntry("controlledProperty",
                                FiwareTypes.TEXT.getKey(),
                                "waterBalance"))
                .build();
        log.info("Persisting water balance measurement: {}", waterBalance);
        deviceMeasurementIntegrationService.persist(waterBalance);
    }

    private DeviceMeasurementBuilder defaultMeasurement(Sensor sensor) {
        return new DeviceMeasurementBuilder()
                .withId(getManufacturerConfiguration() + ":" + sensor.getId())
                .withType(MeasurementType.SOILSCOUT_SENSOR.name())
                .withLocation(sensor.getLocation().getLatitude(), sensor.getLocation().getLongitude())
    }

    private CommonManufacturerConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().soilscout();
    }

}
