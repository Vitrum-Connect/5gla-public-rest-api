package de.app.fivegla.integration.soilscout;


import de.app.fivegla.api.enums.MeasurementType;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.internal.DateTimeAttribute;
import de.app.fivegla.fiware.model.internal.EmptyAttribute;
import de.app.fivegla.fiware.model.internal.NumberAttribute;
import de.app.fivegla.fiware.model.internal.TextAttribute;
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

        var temperature = new DeviceMeasurement(
                getManufacturerConfiguration().fiwarePrefix() + soilScoutSensor.getId(),
                MeasurementType.SOILSCOUT_SENSOR.name(),
                new TextAttribute("temperature"),
                new NumberAttribute(sensorData.getTemperature()),
                new DateTimeAttribute(sensorData.getTimestamp().toInstant()),
                new EmptyAttribute(),
                soilScoutSensor.getLocation().getLatitude(),
                soilScoutSensor.getLocation().getLongitude());
        deviceMeasurementIntegrationService.persist(temperature);

        var moisture = new DeviceMeasurement(
                getManufacturerConfiguration().fiwarePrefix() + soilScoutSensor.getId(),
                MeasurementType.SOILSCOUT_SENSOR.name(),
                new TextAttribute("moisture"),
                new NumberAttribute(sensorData.getMoisture()),
                new DateTimeAttribute(sensorData.getTimestamp().toInstant()),
                new EmptyAttribute(),
                soilScoutSensor.getLocation().getLatitude(),
                soilScoutSensor.getLocation().getLongitude());
        deviceMeasurementIntegrationService.persist(moisture);

        var conductivity = new DeviceMeasurement(
                getManufacturerConfiguration().fiwarePrefix() + soilScoutSensor.getId(),
                MeasurementType.SOILSCOUT_SENSOR.name(),
                new TextAttribute("conductivity"),
                new NumberAttribute(sensorData.getConductivity()),
                new DateTimeAttribute(sensorData.getTimestamp().toInstant()),
                new EmptyAttribute(),
                soilScoutSensor.getLocation().getLatitude(),
                soilScoutSensor.getLocation().getLongitude());
        deviceMeasurementIntegrationService.persist(conductivity);

        var salinity = new DeviceMeasurement(
                getManufacturerConfiguration().fiwarePrefix() + soilScoutSensor.getId(),
                MeasurementType.SOILSCOUT_SENSOR.name(),
                new TextAttribute("salinity"),
                new NumberAttribute(sensorData.getSalinity()),
                new DateTimeAttribute(sensorData.getTimestamp().toInstant()),
                new EmptyAttribute(),
                soilScoutSensor.getLocation().getLatitude(),
                soilScoutSensor.getLocation().getLongitude());
        deviceMeasurementIntegrationService.persist(salinity);

        var waterBalance = new DeviceMeasurement(
                getManufacturerConfiguration().fiwarePrefix() + soilScoutSensor.getId(),
                MeasurementType.SOILSCOUT_SENSOR.name(),
                new TextAttribute("waterBalance"),
                new NumberAttribute(sensorData.getWaterBalance()),
                new DateTimeAttribute(sensorData.getTimestamp().toInstant()),
                new EmptyAttribute(),
                soilScoutSensor.getLocation().getLatitude(),
                soilScoutSensor.getLocation().getLongitude());
        deviceMeasurementIntegrationService.persist(waterBalance);
    }

    private CommonManufacturerConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().soilscout();
    }

}
