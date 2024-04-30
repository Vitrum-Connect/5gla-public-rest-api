package de.app.fivegla.integration.weenat;


import de.app.fivegla.api.enums.MeasurementType;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.internal.DateTimeAttribute;
import de.app.fivegla.fiware.model.internal.EmptyAttribute;
import de.app.fivegla.fiware.model.internal.NumberAttribute;
import de.app.fivegla.fiware.model.internal.TextAttribute;
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
    private final DeviceMeasurementIntegrationService deviceMeasurementIntegrationService;

    public void persist(Tenant tenant, Plot plot, Measurements measurements) {
        var latitude = plot.getLatitude();
        var longitude = plot.getLongitude();
        measurements.getMeasurements().forEach(measurement -> {
            log.info("Persisting measurement for measurement: {}", measurement);

            var temperature = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("temperature"),
                    new NumberAttribute(measurement.getMeasurementValues().getTemperature()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(temperature);

            var relativeHumidity = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("relativeHumidity"),
                    new NumberAttribute(measurement.getMeasurementValues().getRelativeHumidity()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(relativeHumidity);

            var cumulativeRainfall = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("cumulativeRainfall"),
                    new NumberAttribute(measurement.getMeasurementValues().getCumulativeRainfall()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(cumulativeRainfall);

            var windSpeed = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("windSpeed"),
                    new NumberAttribute(measurement.getMeasurementValues().getWindSpeed()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(windSpeed);

            var windGustSpeed = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("windGustSpeed"),
                    new NumberAttribute(measurement.getMeasurementValues().getWindGustSpeed()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(windGustSpeed);

            var soilTemperature = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("soilTemperature"),
                    new NumberAttribute(measurement.getMeasurementValues().getSoilTemperature()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(soilTemperature);

            var soilTemperature15 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("soilTemperature15"),
                    new NumberAttribute(measurement.getMeasurementValues().getSoilTemperature15()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(soilTemperature15);

            var soilTemperature30 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("soilTemperature30"),
                    new NumberAttribute(measurement.getMeasurementValues().getSoilTemperature30()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(soilTemperature30);

            var soilTemperature60 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("soilTemperature60"),
                    new NumberAttribute(measurement.getMeasurementValues().getSoilTemperature60()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(soilTemperature60);

            var soilWaterPotential15 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("soilWaterPotential15"),
                    new NumberAttribute(measurement.getMeasurementValues().getSoilWaterPotential15()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(soilWaterPotential15);

            var soilWaterPotential30 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("soilWaterPotential30"),
                    new NumberAttribute(measurement.getMeasurementValues().getSoilWaterPotential30()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(soilWaterPotential30);

            var soilWaterPotential60 = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("soilWaterPotential60"),
                    new NumberAttribute(measurement.getMeasurementValues().getSoilWaterPotential60()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(soilWaterPotential60);

            var dryTemperature = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("dryTemperature"),
                    new NumberAttribute(measurement.getMeasurementValues().getDryTemperature()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(dryTemperature);

            var wetTemperature = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("wetTemperature"),
                    new NumberAttribute(measurement.getMeasurementValues().getWetTemperature()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(wetTemperature);

            var leafWetnessDuration = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("leafWetnessDuration"),
                    new NumberAttribute(measurement.getMeasurementValues().getLeafWetnessDuration()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(leafWetnessDuration);

            var leafWetnessVoltage = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("leafWetnessVoltage"),
                    new NumberAttribute(measurement.getMeasurementValues().getLeafWetnessVoltage()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(leafWetnessVoltage);

            var solarIrridiance = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("solarIrridiance"),
                    new NumberAttribute(measurement.getMeasurementValues().getSolarIrradiance()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(solarIrridiance);

            var minimumSolarIrridiance = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("minimumSolarIrridiance"),
                    new NumberAttribute(measurement.getMeasurementValues().getMinSolarIrradiance()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(minimumSolarIrridiance);

            var maximumSolarIrridiance = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("maximumSolarIrridiance"),
                    new NumberAttribute(measurement.getMeasurementValues().getMaxSolarIrradiance()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(maximumSolarIrridiance);

            var photosyntheticallyActiveRadiation = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("photosyntheticallyActiveRadiation"),
                    new NumberAttribute(measurement.getMeasurementValues().getPhotosyntheticallyActiveRadiation()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(photosyntheticallyActiveRadiation);

            var minimumPhotosyntheticallyActiveRadiation = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("minimumPhotosyntheticallyActiveRadiation"),
                    new NumberAttribute(measurement.getMeasurementValues().getMinimumPhotosyntheticallyActiveRadiation()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(minimumPhotosyntheticallyActiveRadiation);

            var maximumPhotosyntheticallyActiveRadiation = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("maximumPhotosyntheticallyActiveRadiation"),
                    new NumberAttribute(measurement.getMeasurementValues().getMaximumPhotosyntheticallyActiveRadiation()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(maximumPhotosyntheticallyActiveRadiation);

            var dewPoint = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("dewPoint"),
                    new NumberAttribute(measurement.getMeasurementValues().getDewPoint()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(dewPoint);

            var potentialEvapotranspiration = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + plot.getId(),
                    MeasurementType.WEENAT_SENSOR.getKey(),
                    new TextAttribute("potentialEvapotranspiration"),
                    new NumberAttribute(measurement.getMeasurementValues().getPotentialEvapotranspiration()),
                    new DateTimeAttribute(measurement.getTimestamp()),
                    new EmptyAttribute(),
                    latitude,
                    longitude);
            deviceMeasurementIntegrationService.persist(potentialEvapotranspiration);
        });
    }

}
