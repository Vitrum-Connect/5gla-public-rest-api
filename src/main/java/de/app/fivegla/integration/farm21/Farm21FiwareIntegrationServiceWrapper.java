package de.app.fivegla.integration.farm21;


import de.app.fivegla.api.FiwareDeviceId;
import de.app.fivegla.api.Format;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.Location;
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
            var soilMoisture10 = createDefaultDeviceMeasurement(sensor, sd)
                    .controlledProperty("soilMoisture10")
                    .numValue(sd.getSoilMoisture10())
                    .build();
            log.info("Persisting soil moisture 10: {}", soilMoisture10);
            deviceMeasurementIntegrationService.persist(soilMoisture10);

            var soilMoisture20 = createDefaultDeviceMeasurement(sensor, sd)
                    .controlledProperty("soilMoisture20")
                    .numValue(sd.getSoilMoisture20())
                    .build();
            log.info("Persisting soil moisture 20: {}", soilMoisture20);
            deviceMeasurementIntegrationService.persist(soilMoisture20);

            var soilMoisture30 = createDefaultDeviceMeasurement(sensor, sd)
                    .controlledProperty("soilMoisture30")
                    .numValue(sd.getSoilMoisture30())
                    .build();
            log.info("Persisting soil moisture 30: {}", soilMoisture30);
            deviceMeasurementIntegrationService.persist(soilMoisture30);

            var tempNeg10 = createDefaultDeviceMeasurement(sensor, sd)
                    .controlledProperty("tempNeg10")
                    .numValue(sd.getTempNeg10())
                    .build();
            log.info("Persisting temp neg 10: {}", tempNeg10);
            deviceMeasurementIntegrationService.persist(tempNeg10);

            var humidity = createDefaultDeviceMeasurement(sensor, sd)
                    .controlledProperty("humidity")
                    .numValue(sd.getHumidity())
                    .build();
            log.info("Persisting humidity: {}", humidity);

            var tempPos10 = createDefaultDeviceMeasurement(sensor, sd)
                    .controlledProperty("tempPos10")
                    .numValue(sd.getTempPos10())
                    .build();
            log.info("Persisting temp pos 10: {}", tempPos10);
            deviceMeasurementIntegrationService.persist(tempPos10);

            var battery = createDefaultDeviceMeasurement(sensor, sd)
                    .controlledProperty("battery")
                    .numValue(sd.getBattery())
                    .build();
            log.info("Persisting battery: {}", battery);
            deviceMeasurementIntegrationService.persist(battery);

            var soilTemperature = createDefaultDeviceMeasurement(sensor, sd)
                    .controlledProperty("soilTemperature")
                    .numValue(sd.getSoilTemperature())
                    .build();
            log.info("Persisting soil temperature: {}", soilTemperature);
            deviceMeasurementIntegrationService.persist(soilTemperature);

            var airTemperature = createDefaultDeviceMeasurement(sensor, sd)
                    .controlledProperty("airTemperature")
                    .numValue(sd.getAirTemperature())
                    .build();
            log.info("Persisting air temperature: {}", airTemperature);
            deviceMeasurementIntegrationService.persist(airTemperature);

        });
    }

    private DeviceMeasurement.DeviceMeasurementBuilder createDefaultDeviceMeasurement(Sensor sensor, SensorData sensorData) {
        log.debug("Persisting sensor data for sensor: {}", sensor);
        log.debug("Persisting sensor data: {}", sensorData);
        return DeviceMeasurement.builder()
                .id(FiwareDeviceId.create(getManufacturerConfiguration(), String.valueOf(sensor.getId())))
                .manufacturerSpecificId(String.valueOf(sensor.getId()))
                .dateObserved(Format.format(sensorData.getMeasuredAt()))
                .location(Location.builder()
                        .coordinates(List.of(sensorData.getLatitude(), sensorData.getLongitude()))
                        .build());
    }

    private CommonManufacturerConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().farm21();
    }

}
