package de.app.fivegla.integration.weenat;


import de.app.fivegla.api.enums.EntityType;
import de.app.fivegla.business.GroupService;
import de.app.fivegla.integration.fiware.FiwareEntityIntegrationService;
import de.app.fivegla.integration.fiware.model.DeviceMeasurement;
import de.app.fivegla.integration.fiware.model.internal.EmptyAttribute;
import de.app.fivegla.integration.fiware.model.internal.InstantAttribute;
import de.app.fivegla.integration.fiware.model.internal.NumberAttribute;
import de.app.fivegla.integration.fiware.model.internal.TextAttribute;
import de.app.fivegla.integration.weenat.model.Measurements;
import de.app.fivegla.integration.weenat.model.Plot;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WeenatFiwareIntegrationServiceWrapper {
    private final FiwareEntityIntegrationService fiwareEntityIntegrationService;
    private final GroupService groupService;

    public void persist(Tenant tenant, Plot plot, Measurements measurements) {
        var latitude = plot.getLatitude();
        var longitude = plot.getLongitude();
        var group = groupService.findGroupByTenantAndSensorId(tenant, String.valueOf(plot.getId()));
        if (group.isDefaultGroupForTenant()) {
            log.warn("Looks like the group for the sensor with id {} is not set. We are using the default group for the tenant.", plot.getId());
        }
        measurements.getMeasurements().forEach(measurement -> {
            log.info("Persisting measurement for measurement: {}", measurement);

            var temperature = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("temperature"),
                    new NumberAttribute(measurement.getMeasurementValues().getTemperature()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, temperature);

            var relativeHumidity = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("relativeHumidity"),
                    new NumberAttribute(measurement.getMeasurementValues().getRelativeHumidity()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, relativeHumidity);

            var cumulativeRainfall = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("cumulativeRainfall"),
                    new NumberAttribute(measurement.getMeasurementValues().getCumulativeRainfall()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, cumulativeRainfall);

            var windSpeed = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("windSpeed"),
                    new NumberAttribute(measurement.getMeasurementValues().getWindSpeed()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, windSpeed);

            var windGustSpeed = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("windGustSpeed"),
                    new NumberAttribute(measurement.getMeasurementValues().getWindGustSpeed()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, windGustSpeed);

            var soilTemperature = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("soilTemperature"),
                    new NumberAttribute(measurement.getMeasurementValues().getSoilTemperature()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, soilTemperature);

            var soilTemperature15 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("soilTemperature15"),
                    new NumberAttribute(measurement.getMeasurementValues().getSoilTemperature15()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, soilTemperature15);

            var soilTemperature30 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("soilTemperature30"),
                    new NumberAttribute(measurement.getMeasurementValues().getSoilTemperature30()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, soilTemperature30);

            var soilTemperature60 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("soilTemperature60"),
                    new NumberAttribute(measurement.getMeasurementValues().getSoilTemperature60()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, soilTemperature60);

            var soilWaterPotential15 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("soilWaterPotential15"),
                    new NumberAttribute(measurement.getMeasurementValues().getSoilWaterPotential15()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, soilWaterPotential15);

            var soilWaterPotential30 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("soilWaterPotential30"),
                    new NumberAttribute(measurement.getMeasurementValues().getSoilWaterPotential30()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, soilWaterPotential30);

            var soilWaterPotential60 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("soilWaterPotential60"),
                    new NumberAttribute(measurement.getMeasurementValues().getSoilWaterPotential60()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, soilWaterPotential60);

            var dryTemperature = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("dryTemperature"),
                    new NumberAttribute(measurement.getMeasurementValues().getDryTemperature()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, dryTemperature);

            var wetTemperature = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("wetTemperature"),
                    new NumberAttribute(measurement.getMeasurementValues().getWetTemperature()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, wetTemperature);

            var leafWetnessDuration = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("leafWetnessDuration"),
                    new NumberAttribute(measurement.getMeasurementValues().getLeafWetnessDuration()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, leafWetnessDuration);

            var leafWetnessVoltage = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("leafWetnessVoltage"),
                    new NumberAttribute(measurement.getMeasurementValues().getLeafWetnessVoltage()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, leafWetnessVoltage);

            var solarIrridiance = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("solarIrridiance"),
                    new NumberAttribute(measurement.getMeasurementValues().getSolarIrradiance()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, solarIrridiance);

            var minimumSolarIrridiance = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("minimumSolarIrridiance"),
                    new NumberAttribute(measurement.getMeasurementValues().getMinSolarIrradiance()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, minimumSolarIrridiance);

            var maximumSolarIrridiance = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("maximumSolarIrridiance"),
                    new NumberAttribute(measurement.getMeasurementValues().getMaxSolarIrradiance()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, maximumSolarIrridiance);

            var photosyntheticallyActiveRadiation = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("photosyntheticallyActiveRadiation"),
                    new NumberAttribute(measurement.getMeasurementValues().getPhotosyntheticallyActiveRadiation()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, photosyntheticallyActiveRadiation);

            var minimumPhotosyntheticallyActiveRadiation = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("minimumPhotosyntheticallyActiveRadiation"),
                    new NumberAttribute(measurement.getMeasurementValues().getMinimumPhotosyntheticallyActiveRadiation()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, minimumPhotosyntheticallyActiveRadiation);

            var maximumPhotosyntheticallyActiveRadiation = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("maximumPhotosyntheticallyActiveRadiation"),
                    new NumberAttribute(measurement.getMeasurementValues().getMaximumPhotosyntheticallyActiveRadiation()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, maximumPhotosyntheticallyActiveRadiation);

            var dewPoint = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("dewPoint"),
                    new NumberAttribute(measurement.getMeasurementValues().getDewPoint()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, dewPoint);

            var potentialEvapotranspiration = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    EntityType.WEENAT_SENSOR.getKey(),
                    new TextAttribute(group.getOid()),
                    new TextAttribute("potentialEvapotranspiration"),
                    new NumberAttribute(measurement.getMeasurementValues().getPotentialEvapotranspiration()),
                    new InstantAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            fiwareEntityIntegrationService.persist(tenant, group, potentialEvapotranspiration);
        });
    }

}
