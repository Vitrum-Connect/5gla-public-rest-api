package de.app.fivegla.integration.farm21;


import de.app.fivegla.api.enums.MeasurementType;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.api.FiwareTypes;
import de.app.fivegla.fiware.model.builder.DeviceMeasurementBuilder;
import de.app.fivegla.integration.farm21.model.Sensor;
import de.app.fivegla.integration.farm21.model.SensorData;
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
public class Farm21FiwareIntegrationServiceWrapper {
    private final DeviceMeasurementIntegrationService deviceMeasurementIntegrationService;
    private final ApplicationConfiguration applicationConfiguration;

    /**
     * Create Farm21 sensor data in FIWARE.
     *
     * @param sensor     the sensor
     * @param sensorData the sensor data to create
     */
    public void persist(Sensor sensor, List<SensorData> sensorData) {
        sensorData.forEach(sd -> {
            var soilMoisture10 = defaultDeviceMeasurement(sensor, sd)
                    .withMeasurement("soilMoisture10",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(sd.getSoilMoisture10()),
                            sd.getMeasuredAt(),
                            new DeviceMeasurementBuilder.MetadataEntry("controlledProperty",
                                    FiwareTypes.TEXT.getKey(),
                                    "soilMoisture10"))
                    .build();
            log.info("Persisting soil moisture 10: {}", soilMoisture10);
            deviceMeasurementIntegrationService.persist(soilMoisture10);

            var soilMoisture20 = defaultDeviceMeasurement(sensor, sd)
                    .withMeasurement("soilMoisture20",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(sd.getSoilMoisture20()),
                            sd.getMeasuredAt(),
                            new DeviceMeasurementBuilder.MetadataEntry("controlledProperty",
                                    FiwareTypes.TEXT.getKey(),
                                    "soilMoisture20"))
                    .build();
            log.info("Persisting soil moisture 20: {}", soilMoisture20);
            deviceMeasurementIntegrationService.persist(soilMoisture20);

            var soilMoisture30 = defaultDeviceMeasurement(sensor, sd)
                    .withMeasurement("soilMoisture30",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(sd.getSoilMoisture30()),
                            sd.getMeasuredAt(),
                            new DeviceMeasurementBuilder.MetadataEntry("controlledProperty",
                                    FiwareTypes.TEXT.getKey(),
                                    "soilMoisture30"))
                    .build();
            log.info("Persisting soil moisture 30: {}", soilMoisture30);
            deviceMeasurementIntegrationService.persist(soilMoisture30);

            var tempNeg10 = defaultDeviceMeasurement(sensor, sd)
                    .withMeasurement("tempNeg10",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(sd.getTempNeg10()),
                            sd.getMeasuredAt(),
                            new DeviceMeasurementBuilder.MetadataEntry("controlledProperty",
                                    FiwareTypes.TEXT.getKey(),
                                    "tempNeg10"))
                    .build();
            log.info("Persisting temp neg 10: {}", tempNeg10);
            deviceMeasurementIntegrationService.persist(tempNeg10);

            var humidity = defaultDeviceMeasurement(sensor, sd)
                    .withMeasurement("humidity",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(sd.getHumidity()),
                            sd.getMeasuredAt(),
                            new DeviceMeasurementBuilder.MetadataEntry("controlledProperty",
                                    FiwareTypes.TEXT.getKey(),
                                    "humidity"))
                    .build();
            log.info("Persisting humidity: {}", humidity);
            deviceMeasurementIntegrationService.persist(humidity);

            var tempPos10 = defaultDeviceMeasurement(sensor, sd)
                    .withMeasurement("tempPos10",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(sd.getTempPos10()),
                            sd.getMeasuredAt(),
                            new DeviceMeasurementBuilder.MetadataEntry("controlledProperty",
                                    FiwareTypes.TEXT.getKey(),
                                    "tempPos10"))
                    .build();
            log.info("Persisting temp pos 10: {}", tempPos10);
            deviceMeasurementIntegrationService.persist(tempPos10);

            var battery = defaultDeviceMeasurement(sensor, sd)
                    .withMeasurement("battery",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(sd.getBattery()),
                            sd.getMeasuredAt(),
                            new DeviceMeasurementBuilder.MetadataEntry("controlledProperty",
                                    FiwareTypes.TEXT.getKey(),
                                    "battery"))
                    .build();
            log.info("Persisting battery: {}", battery);
            deviceMeasurementIntegrationService.persist(battery);

            var soilTemperature = defaultDeviceMeasurement(sensor, sd)
                    .withMeasurement("soilTemperature",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(sd.getSoilTemperature()),
                            sd.getMeasuredAt(),
                            new DeviceMeasurementBuilder.MetadataEntry("controlledProperty",
                                    FiwareTypes.TEXT.getKey(),
                                    "soilTemperature"))
                    .build();
            log.info("Persisting soil temperature: {}", soilTemperature);
            deviceMeasurementIntegrationService.persist(soilTemperature);

            var airTemperature = defaultDeviceMeasurement(sensor, sd)
                    .withMeasurement("airTemperature",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(sd.getAirTemperature()),
                            sd.getMeasuredAt(),
                            new DeviceMeasurementBuilder.MetadataEntry("controlledProperty",
                                    FiwareTypes.TEXT.getKey(),
                                    "airTemperature"))
                    .build();
            log.info("Persisting air temperature: {}", airTemperature);
            deviceMeasurementIntegrationService.persist(airTemperature);
        });
    }

    private DeviceMeasurementBuilder defaultDeviceMeasurement(Sensor sensor, SensorData sensorData) {
        log.debug("Persisting sensor data for sensor: {}", sensor);
        log.debug("Persisting sensor data: {}", sensorData);
        return new DeviceMeasurementBuilder()
                .withId(getManufacturerConfiguration().fiwarePrefix() + sensor.getId())
                .withType(MeasurementType.FARM21_SENSOR.name())
                .withLocation(sensorData.getLatitude(), sensorData.getLongitude());
    }

    private CommonManufacturerConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().farm21();
    }

}
