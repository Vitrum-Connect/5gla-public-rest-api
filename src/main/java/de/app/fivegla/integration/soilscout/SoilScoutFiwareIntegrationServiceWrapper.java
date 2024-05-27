package de.app.fivegla.integration.soilscout;


import de.app.fivegla.api.enums.EntityType;
import de.app.fivegla.business.GroupService;
import de.app.fivegla.integration.fiware.FiwareEntityIntegrationService;
import de.app.fivegla.integration.fiware.model.DeviceMeasurement;
import de.app.fivegla.integration.fiware.model.internal.DateTimeAttribute;
import de.app.fivegla.integration.fiware.model.internal.EmptyAttribute;
import de.app.fivegla.integration.fiware.model.internal.NumberAttribute;
import de.app.fivegla.integration.fiware.model.internal.TextAttribute;
import de.app.fivegla.integration.soilscout.model.SensorData;
import de.app.fivegla.persistence.entity.Tenant;
import de.app.fivegla.persistence.entity.ThirdPartyApiConfiguration;
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
    private final FiwareEntityIntegrationService fiwareEntityIntegrationService;
    private final GroupService groupService;

    /**
     * Create soil scout sensor data in FIWARE.
     *
     * @param sensorData the sensor data to create
     */
    public void persist(Tenant tenant, ThirdPartyApiConfiguration thirdPartyApiConfiguration, SensorData sensorData) {
        var soilScoutSensor = soilScoutSensorIntegrationService.fetch(thirdPartyApiConfiguration, sensorData.getDevice());
        var group = groupService.findGroupByTenantAndSensorId(tenant, String.valueOf(sensorData.getDevice()));
        if (group.isDefaultGroupForTenant()) {
            log.warn("Looks like the group for the sensor with id {} is not set. We are using the default group for the tenant.", sensorData.getDevice());
        }

        var temperature = new DeviceMeasurement(
                tenant.getFiwarePrefix() + soilScoutSensor.getId(),
                EntityType.SOILSCOUT_SENSOR.getKey(),
                new TextAttribute(group.getOid()),
                new TextAttribute("temperature"),
                new NumberAttribute(sensorData.getTemperature()),
                new DateTimeAttribute(sensorData.getTimestamp().toInstant()),
                new EmptyAttribute(),
                soilScoutSensor.getLocation().getLatitude(),
                soilScoutSensor.getLocation().getLongitude());
        fiwareEntityIntegrationService.persist(tenant, group, temperature);

        var moisture = new DeviceMeasurement(
                tenant.getFiwarePrefix() + soilScoutSensor.getId(),
                EntityType.SOILSCOUT_SENSOR.getKey(),
                new TextAttribute(group.getOid()),
                new TextAttribute("moisture"),
                new NumberAttribute(sensorData.getMoisture()),
                new DateTimeAttribute(sensorData.getTimestamp().toInstant()),
                new EmptyAttribute(),
                soilScoutSensor.getLocation().getLatitude(),
                soilScoutSensor.getLocation().getLongitude());
        fiwareEntityIntegrationService.persist(tenant, group, moisture);

        var conductivity = new DeviceMeasurement(
                tenant.getFiwarePrefix() + soilScoutSensor.getId(),
                EntityType.SOILSCOUT_SENSOR.getKey(),
                new TextAttribute(group.getOid()),
                new TextAttribute("conductivity"),
                new NumberAttribute(sensorData.getConductivity()),
                new DateTimeAttribute(sensorData.getTimestamp().toInstant()),
                new EmptyAttribute(),
                soilScoutSensor.getLocation().getLatitude(),
                soilScoutSensor.getLocation().getLongitude());
        fiwareEntityIntegrationService.persist(tenant, group, conductivity);

        var salinity = new DeviceMeasurement(
                tenant.getFiwarePrefix() + soilScoutSensor.getId(),
                EntityType.SOILSCOUT_SENSOR.getKey(),
                new TextAttribute(group.getOid()),
                new TextAttribute("salinity"),
                new NumberAttribute(sensorData.getSalinity()),
                new DateTimeAttribute(sensorData.getTimestamp().toInstant()),
                new EmptyAttribute(),
                soilScoutSensor.getLocation().getLatitude(),
                soilScoutSensor.getLocation().getLongitude());
        fiwareEntityIntegrationService.persist(tenant, group, salinity);

        var waterBalance = new DeviceMeasurement(
                tenant.getFiwarePrefix() + soilScoutSensor.getId(),
                EntityType.SOILSCOUT_SENSOR.getKey(),
                new TextAttribute(group.getOid()),
                new TextAttribute("waterBalance"),
                new NumberAttribute(sensorData.getWaterBalance()),
                new DateTimeAttribute(sensorData.getTimestamp().toInstant()),
                new EmptyAttribute(),
                soilScoutSensor.getLocation().getLatitude(),
                soilScoutSensor.getLocation().getLongitude());
        fiwareEntityIntegrationService.persist(tenant, group, waterBalance);
    }

}
