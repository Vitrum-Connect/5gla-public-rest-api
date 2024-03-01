package de.app.fivegla.integration.weenat;


import de.app.fivegla.api.enums.MeasurementType;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.WeenatConfiguration;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.api.FiwareType;
import de.app.fivegla.fiware.model.builder.DeviceMeasurementBuilder;
import de.app.fivegla.integration.weenat.model.Measurement;
import de.app.fivegla.integration.weenat.model.Measurements;
import de.app.fivegla.integration.weenat.model.Plot;
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
    private final ApplicationConfiguration applicationConfiguration;

    public void persist(Plot plot, Measurements measurements) {
        var latitude = plot.getLatitude();
        var longitude = plot.getLongitude();
        measurements.getMeasurements().forEach(measurement -> {
            log.info("Persisting measurement for measurement: {}", measurement);

            var temperature = defaultMeasurement(plot, measurement)
                    .withMeasurement("temperature",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getTemperature()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(temperature);

            var relativeHumidity = defaultMeasurement(plot, measurement)
                    .withMeasurement("relativeHumidity",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getRelativeHumidity()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(relativeHumidity);

            var cumulativeRainfall = defaultMeasurement(plot, measurement)
                    .withMeasurement("cumulativeRainfall",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getCumulativeRainfall()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(cumulativeRainfall);

            var windSpeed = defaultMeasurement(plot, measurement)
                    .withMeasurement("windSpeed",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getWindSpeed()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(windSpeed);

            var windGustSpeed = defaultMeasurement(plot, measurement)
                    .withMeasurement("windGustSpeed",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getWindGustSpeed()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(windGustSpeed);

            var soilTemperature = defaultMeasurement(plot, measurement)
                    .withMeasurement("soilTemperature",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getSoilTemperature()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(soilTemperature);

            var soilTemperature15 = defaultMeasurement(plot, measurement)
                    .withMeasurement("soilTemperature15",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getSoilTemperature15()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(soilTemperature15);

            var soilTemperature30 = defaultMeasurement(plot, measurement)
                    .withMeasurement("soilTemperature30",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getSoilTemperature30()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(soilTemperature30);

            var soilTemperature60 = defaultMeasurement(plot, measurement)
                    .withMeasurement("soilTemperature60",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getSoilTemperature60()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(soilTemperature60);

            var soilWaterPotential15 = defaultMeasurement(plot, measurement)
                    .withMeasurement("soilWaterPotential15",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getSoilWaterPotential15()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(soilWaterPotential15);

            var soilWaterPotential30 = defaultMeasurement(plot, measurement)
                    .withMeasurement("soilWaterPotential30",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getSoilWaterPotential30()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(soilWaterPotential30);

            var soilWaterPotential60 = defaultMeasurement(plot, measurement)
                    .withMeasurement("soilWaterPotential60",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getSoilWaterPotential60()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(soilWaterPotential60);

            var dryTemperature = defaultMeasurement(plot, measurement)
                    .withMeasurement("dryTemperature",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getDryTemperature()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(dryTemperature);

            var wetTemperature = defaultMeasurement(plot, measurement)
                    .withMeasurement("wetTemperature",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getWetTemperature()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(wetTemperature);

            var leafWetnessDuration = defaultMeasurement(plot, measurement)
                    .withMeasurement("leafWetnessDuration",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getLeafWetnessDuration()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(leafWetnessDuration);

            var leafWetnessVoltage = defaultMeasurement(plot, measurement)
                    .withMeasurement("leafWetnessVoltage",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getLeafWetnessVoltage()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(leafWetnessVoltage);

            var solarIrridiance = defaultMeasurement(plot, measurement)
                    .withMeasurement("solarIrridiance",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getSolarIrradiance()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(solarIrridiance);

            var minimumSolarIrridiance = defaultMeasurement(plot, measurement)
                    .withMeasurement("minimumSolarIrridiance",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getMinSolarIrradiance()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(minimumSolarIrridiance);

            var maximumSolarIrridiance = defaultMeasurement(plot, measurement)
                    .withMeasurement("maximumSolarIrridiance",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getMaxSolarIrradiance()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(maximumSolarIrridiance);

            var photosyntheticallyActiveRadiation = defaultMeasurement(plot, measurement)
                    .withMeasurement("photosyntheticallyActiveRadiation",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getPhotosyntheticallyActiveRadiation()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(photosyntheticallyActiveRadiation);

            var minimumPhotosyntheticallyActiveRadiation = defaultMeasurement(plot, measurement)
                    .withMeasurement("minimumPhotosyntheticallyActiveRadiation",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getMinimumPhotosyntheticallyActiveRadiation()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(minimumPhotosyntheticallyActiveRadiation);

            var maximumPhotosyntheticallyActiveRadiation = defaultMeasurement(plot, measurement)
                    .withMeasurement("maximumPhotosyntheticallyActiveRadiation",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getMaximumPhotosyntheticallyActiveRadiation()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(maximumPhotosyntheticallyActiveRadiation);

            var dewPoint = defaultMeasurement(plot, measurement)
                    .withMeasurement("dewPoint",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getDewPoint()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(dewPoint);

            var potentialEvapotranspiration = defaultMeasurement(plot, measurement)
                    .withMeasurement("potentialEvapotranspiration",
                            FiwareType.TEXT,
                            String.valueOf(measurement.getMeasurementValues().getPotentialEvapotranspiration()),
                            measurement.getTimestamp(),
                            latitude,
                            longitude)
                    .build();
            deviceMeasurementIntegrationService.persist(potentialEvapotranspiration);
        });
    }

    private DeviceMeasurementBuilder defaultMeasurement(Plot plot, Measurement measurement) {
        log.debug("Persisting probe data for probe: {}", plot);
        log.debug("Persisting measurement data: {}", measurement);
        return new DeviceMeasurementBuilder()
                .withId(getManufacturerConfiguration().fiwarePrefix() + plot.getId())
                .withType(MeasurementType.WEENAT_SENSOR.name());
    }

    private WeenatConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().weenat();
    }

}
