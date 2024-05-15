package de.app.fivegla.integration.farm21;


import de.app.fivegla.api.enums.EntityType;
import de.app.fivegla.business.GroupService;
import de.app.fivegla.config.InternalBeanConfiguration;
import de.app.fivegla.integration.farm21.model.Sensor;
import de.app.fivegla.integration.farm21.model.SensorData;
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
    private final InternalBeanConfiguration internalBeanConfiguration;
    private final GroupService groupService;

    /**
     * Create Farm21 sensor data in FIWARE.
     *
     * @param sensor     the sensor
     * @param sensorData the sensor data to create
     */
    public void persist(Tenant tenant, Sensor sensor, List<SensorData> sensorData) {
        var group = groupService.findGroupByTenantAndSensorId(tenant, String.valueOf(sensor.getId()));
        if (group.isDefaultGroupForTenant()) {
            log.warn("Looks like the group for the sensor with id {} is not set. We are using the default group for the tenant.", sensor.getId());
        }
        sensorData.forEach(sd -> {
            var soilMoisture10 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + sensor.getId(),
                    EntityType.FARM21_SENSOR.getKey(),
                    new TextAttribute(group.getGroupId()),
                    new TextAttribute("soilMoisture10"),
                    new NumberAttribute(sd.getSoilMoisture10()),
                    new DateTimeAttribute(sd.getMeasuredAt()),
                    new EmptyAttribute(),
                    sd.getLatitude(),
                    sd.getLongitude());
            internalBeanConfiguration.fiwareEntityIntegrationService(tenant.getTenantId()).persist(soilMoisture10);

            var soilMoisture20 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + sensor.getId(),
                    EntityType.FARM21_SENSOR.getKey(),
                    new TextAttribute(group.getGroupId()),
                    new TextAttribute("soilMoisture20"),
                    new NumberAttribute(sd.getSoilMoisture20()),
                    new DateTimeAttribute(sd.getMeasuredAt()),
                    new EmptyAttribute(),
                    sd.getLatitude(),
                    sd.getLongitude());
            internalBeanConfiguration.fiwareEntityIntegrationService(tenant.getTenantId()).persist(soilMoisture20);

            var soilMoisture30 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + sensor.getId(),
                    EntityType.FARM21_SENSOR.getKey(),
                    new TextAttribute(group.getGroupId()),
                    new TextAttribute("soilMoisture30"),
                    new NumberAttribute(sd.getSoilMoisture30()),
                    new DateTimeAttribute(sd.getMeasuredAt()),
                    new EmptyAttribute(),
                    sd.getLatitude(),
                    sd.getLongitude());
            internalBeanConfiguration.fiwareEntityIntegrationService(tenant.getTenantId()).persist(soilMoisture30);

            var tempNeg10 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + sensor.getId(),
                    EntityType.FARM21_SENSOR.getKey(),
                    new TextAttribute(group.getGroupId()),
                    new TextAttribute("tempNeg10"),
                    new NumberAttribute(sd.getTempNeg10()),
                    new DateTimeAttribute(sd.getMeasuredAt()),
                    new EmptyAttribute(),
                    sd.getLatitude(),
                    sd.getLongitude());
            internalBeanConfiguration.fiwareEntityIntegrationService(tenant.getTenantId()).persist(tempNeg10);

            var humidity = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + sensor.getId(),
                    EntityType.FARM21_SENSOR.getKey(),
                    new TextAttribute(group.getGroupId()),
                    new TextAttribute("humidity"),
                    new NumberAttribute(sd.getHumidity()),
                    new DateTimeAttribute(sd.getMeasuredAt()),
                    new EmptyAttribute(),
                    sd.getLatitude(),
                    sd.getLongitude());
            internalBeanConfiguration.fiwareEntityIntegrationService(tenant.getTenantId()).persist(humidity);

            var tempPos10 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + sensor.getId(),
                    EntityType.FARM21_SENSOR.getKey(),
                    new TextAttribute(group.getGroupId()),
                    new TextAttribute("tempPos10"),
                    new NumberAttribute(sd.getTempPos10()),
                    new DateTimeAttribute(sd.getMeasuredAt()),
                    new EmptyAttribute(),
                    sd.getLatitude(),
                    sd.getLongitude());
            internalBeanConfiguration.fiwareEntityIntegrationService(tenant.getTenantId()).persist(tempPos10);

            var battery = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + sensor.getId(),
                    EntityType.FARM21_SENSOR.getKey(),
                    new TextAttribute(group.getGroupId()),
                    new TextAttribute("battery"),
                    new NumberAttribute(sd.getBattery()),
                    new DateTimeAttribute(sd.getMeasuredAt()),
                    new EmptyAttribute(),
                    sd.getLatitude(),
                    sd.getLongitude());
            internalBeanConfiguration.fiwareEntityIntegrationService(tenant.getTenantId()).persist(battery);

            var soilTemperature = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + sensor.getId(),
                    EntityType.FARM21_SENSOR.getKey(),
                    new TextAttribute(group.getGroupId()),
                    new TextAttribute("soilTemperature"),
                    new NumberAttribute(sd.getSoilTemperature()),
                    new DateTimeAttribute(sd.getMeasuredAt()),
                    new EmptyAttribute(),
                    sd.getLatitude(),
                    sd.getLongitude());
            internalBeanConfiguration.fiwareEntityIntegrationService(tenant.getTenantId()).persist(soilTemperature);

            var airTemperature = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + sensor.getId(),
                    EntityType.FARM21_SENSOR.getKey(),
                    new TextAttribute(group.getGroupId()),
                    new TextAttribute("airTemperature"),
                    new NumberAttribute(sd.getAirTemperature()),
                    new DateTimeAttribute(sd.getMeasuredAt()),
                    new EmptyAttribute(),
                    sd.getLatitude(),
                    sd.getLongitude());
            internalBeanConfiguration.fiwareEntityIntegrationService(tenant.getTenantId()).persist(airTemperature);
        });
    }

}
