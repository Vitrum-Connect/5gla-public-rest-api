package de.app.fivegla.integration.farm21;


import de.app.fivegla.api.ZoneOrDefaultValue;
import de.app.fivegla.api.enums.EntityType;
import de.app.fivegla.integration.farm21.model.Sensor;
import de.app.fivegla.integration.farm21.model.SensorData;
import de.app.fivegla.integration.fiware.FiwareEntityIntegrationService;
import de.app.fivegla.integration.fiware.model.DeviceMeasurement;
import de.app.fivegla.integration.fiware.model.internal.DateTimeAttribute;
import de.app.fivegla.integration.fiware.model.internal.EmptyAttribute;
import de.app.fivegla.integration.fiware.model.internal.NumberAttribute;
import de.app.fivegla.integration.fiware.model.internal.TextAttribute;
import de.app.fivegla.persistence.entity.Tenant;
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
    private final FiwareEntityIntegrationService fiwareEntityIntegrationService;

    /**
     * Create Farm21 sensor data in FIWARE.
     *
     * @param sensor     the sensor
     * @param sensorData the sensor data to create
     */
    public void persist(Tenant tenant, Sensor sensor, List<SensorData> sensorData) {
        sensorData.forEach(sd -> {
            var soilMoisture10 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + sensor.getId(),
                    EntityType.FARM21_SENSOR.getKey(),
                    ZoneOrDefaultValue.DEFAULT,
                    new TextAttribute("soilMoisture10"),
                    new NumberAttribute(sd.getSoilMoisture10()),
                    new DateTimeAttribute(sd.getMeasuredAt()),
                    new EmptyAttribute(),
                    sd.getLatitude(),
                    sd.getLongitude());
            fiwareEntityIntegrationService.persist(soilMoisture10);

            var soilMoisture20 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + sensor.getId(),
                    EntityType.FARM21_SENSOR.getKey(),
                    ZoneOrDefaultValue.DEFAULT,
                    new TextAttribute("soilMoisture20"),
                    new NumberAttribute(sd.getSoilMoisture20()),
                    new DateTimeAttribute(sd.getMeasuredAt()),
                    new EmptyAttribute(),
                    sd.getLatitude(),
                    sd.getLongitude());
            fiwareEntityIntegrationService.persist(soilMoisture20);

            var soilMoisture30 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + sensor.getId(),
                    EntityType.FARM21_SENSOR.getKey(),
                    ZoneOrDefaultValue.DEFAULT,
                    new TextAttribute("soilMoisture30"),
                    new NumberAttribute(sd.getSoilMoisture30()),
                    new DateTimeAttribute(sd.getMeasuredAt()),
                    new EmptyAttribute(),
                    sd.getLatitude(),
                    sd.getLongitude());
            fiwareEntityIntegrationService.persist(soilMoisture30);

            var tempNeg10 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + sensor.getId(),
                    EntityType.FARM21_SENSOR.getKey(),
                    ZoneOrDefaultValue.DEFAULT,
                    new TextAttribute("tempNeg10"),
                    new NumberAttribute(sd.getTempNeg10()),
                    new DateTimeAttribute(sd.getMeasuredAt()),
                    new EmptyAttribute(),
                    sd.getLatitude(),
                    sd.getLongitude());
            fiwareEntityIntegrationService.persist(tempNeg10);

            var humidity = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + sensor.getId(),
                    EntityType.FARM21_SENSOR.getKey(),
                    ZoneOrDefaultValue.DEFAULT,
                    new TextAttribute("humidity"),
                    new NumberAttribute(sd.getHumidity()),
                    new DateTimeAttribute(sd.getMeasuredAt()),
                    new EmptyAttribute(),
                    sd.getLatitude(),
                    sd.getLongitude());
            fiwareEntityIntegrationService.persist(humidity);

            var tempPos10 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + sensor.getId(),
                    EntityType.FARM21_SENSOR.getKey(),
                    ZoneOrDefaultValue.DEFAULT,
                    new TextAttribute("tempPos10"),
                    new NumberAttribute(sd.getTempPos10()),
                    new DateTimeAttribute(sd.getMeasuredAt()),
                    new EmptyAttribute(),
                    sd.getLatitude(),
                    sd.getLongitude());
            fiwareEntityIntegrationService.persist(tempPos10);

            var battery = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + sensor.getId(),
                    EntityType.FARM21_SENSOR.getKey(),
                    ZoneOrDefaultValue.DEFAULT,
                    new TextAttribute("battery"),
                    new NumberAttribute(sd.getBattery()),
                    new DateTimeAttribute(sd.getMeasuredAt()),
                    new EmptyAttribute(),
                    sd.getLatitude(),
                    sd.getLongitude());
            fiwareEntityIntegrationService.persist(battery);

            var soilTemperature = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + sensor.getId(),
                    EntityType.FARM21_SENSOR.getKey(),
                    ZoneOrDefaultValue.DEFAULT,
                    new TextAttribute("soilTemperature"),
                    new NumberAttribute(sd.getSoilTemperature()),
                    new DateTimeAttribute(sd.getMeasuredAt()),
                    new EmptyAttribute(),
                    sd.getLatitude(),
                    sd.getLongitude());
            fiwareEntityIntegrationService.persist(soilTemperature);

            var airTemperature = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + sensor.getId(),
                    EntityType.FARM21_SENSOR.getKey(),
                    ZoneOrDefaultValue.DEFAULT,
                    new TextAttribute("airTemperature"),
                    new NumberAttribute(sd.getAirTemperature()),
                    new DateTimeAttribute(sd.getMeasuredAt()),
                    new EmptyAttribute(),
                    sd.getLatitude(),
                    sd.getLongitude());
            fiwareEntityIntegrationService.persist(airTemperature);
        });
    }

}
