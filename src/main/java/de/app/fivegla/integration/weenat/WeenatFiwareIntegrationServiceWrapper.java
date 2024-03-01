package de.app.fivegla.integration.weenat;


import de.app.fivegla.api.enums.MeasurementType;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.WeenatConfiguration;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.api.FiwareMetadataTypes;
import de.app.fivegla.fiware.api.FiwareTypes;
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
        measurements.getMeasurements().forEach(measurement -> {
            log.info("Persisting measurement for measurement: {}", measurement);

            var temperature = defaultMeasurement(plot, measurement)
                    .withMeasurement("temperature",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getTemperature()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "temperature"))
                    .build();
            deviceMeasurementIntegrationService.persist(temperature);

            var relativeHumidity = defaultMeasurement(plot, measurement)
                    .withMeasurement("relativeHumidity",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getRelativeHumidity()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "relativeHumidity"))
                    .build();
            deviceMeasurementIntegrationService.persist(relativeHumidity);

            var cumulativeRainfall = defaultMeasurement(plot, measurement)
                    .withMeasurement("cumulativeRainfall",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getCumulativeRainfall()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "cumulativeRainfall"))
                    .build();
            deviceMeasurementIntegrationService.persist(cumulativeRainfall);

            var windSpeed = defaultMeasurement(plot, measurement)
                    .withMeasurement("windSpeed",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getWindSpeed()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "windSpeed"))
                    .build();
            deviceMeasurementIntegrationService.persist(windSpeed);

            var windGustSpeed = defaultMeasurement(plot, measurement)
                    .withMeasurement("windGustSpeed",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getWindGustSpeed()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "windGustSpeed"))
                    .build();
            deviceMeasurementIntegrationService.persist(windGustSpeed);

            var soilTemperature = defaultMeasurement(plot, measurement)
                    .withMeasurement("soilTemperature",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getSoilTemperature()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "soilTemperature"))
                    .build();
            deviceMeasurementIntegrationService.persist(soilTemperature);

            var soilTemperature15 = defaultMeasurement(plot, measurement)
                    .withMeasurement("soilTemperature15",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getSoilTemperature15()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "soilTemperature15"))
                    .build();
            deviceMeasurementIntegrationService.persist(soilTemperature15);

            var soilTemperature30 = defaultMeasurement(plot, measurement)
                    .withMeasurement("soilTemperature30",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getSoilTemperature30()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "soilTemperature30"))
                    .build();
            deviceMeasurementIntegrationService.persist(soilTemperature30);

            var soilTemperature60 = defaultMeasurement(plot, measurement)
                    .withMeasurement("soilTemperature60",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getSoilTemperature60()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "soilTemperature60"))
                    .build();
            deviceMeasurementIntegrationService.persist(soilTemperature60);

            var soilWaterPotential15 = defaultMeasurement(plot, measurement)
                    .withMeasurement("soilWaterPotential15",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getSoilWaterPotential15()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "soilWaterPotential15"))
                    .build();
            deviceMeasurementIntegrationService.persist(soilWaterPotential15);

            var soilWaterPotential30 = defaultMeasurement(plot, measurement)
                    .withMeasurement("soilWaterPotential30",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getSoilWaterPotential30()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "soilWaterPotential30"))
                    .build();
            deviceMeasurementIntegrationService.persist(soilWaterPotential30);

            var soilWaterPotential60 = defaultMeasurement(plot, measurement)
                    .withMeasurement("soilWaterPotential60",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getSoilWaterPotential60()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "soilWaterPotential60"))
                    .build();
            deviceMeasurementIntegrationService.persist(soilWaterPotential60);

            var dryTemperature = defaultMeasurement(plot, measurement)
                    .withMeasurement("dryTemperature",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getDryTemperature()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "dryTemperature"))
                    .build();
            deviceMeasurementIntegrationService.persist(dryTemperature);

            var wetTemperature = defaultMeasurement(plot, measurement)
                    .withMeasurement("wetTemperature",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getWetTemperature()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "wetTemperature"))
                    .build();
            deviceMeasurementIntegrationService.persist(wetTemperature);

            var leafWetnessDuration = defaultMeasurement(plot, measurement)
                    .withMeasurement("leafWetnessDuration",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getLeafWetnessDuration()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "leafWetnessDuration"))
                    .build();
            deviceMeasurementIntegrationService.persist(leafWetnessDuration);

            var leafWetnessVoltage = defaultMeasurement(plot, measurement)
                    .withMeasurement("leafWetnessVoltage",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getLeafWetnessVoltage()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "leafWetnessVoltage"))
                    .build();
            deviceMeasurementIntegrationService.persist(leafWetnessVoltage);

            var solarIrridiance = defaultMeasurement(plot, measurement)
                    .withMeasurement("solarIrridiance",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getSolarIrradiance()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "solarIrridiance"))
                    .build();
            deviceMeasurementIntegrationService.persist(solarIrridiance);

            var minimumSolarIrridiance = defaultMeasurement(plot, measurement)
                    .withMeasurement("minimumSolarIrridiance",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getMinSolarIrradiance()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "minimumSolarIrridiance"))
                    .build();
            deviceMeasurementIntegrationService.persist(minimumSolarIrridiance);

            var maximumSolarIrridiance = defaultMeasurement(plot, measurement)
                    .withMeasurement("maximumSolarIrridiance",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getMaxSolarIrradiance()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "maximumSolarIrridiance"))
                    .build();
            deviceMeasurementIntegrationService.persist(maximumSolarIrridiance);

            var photosyntheticallyActiveRadiation = defaultMeasurement(plot, measurement)
                    .withMeasurement("photosyntheticallyActiveRadiation",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getPhotosyntheticallyActiveRadiation()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "photosyntheticallyActiveRadiation"))
                    .build();
            deviceMeasurementIntegrationService.persist(photosyntheticallyActiveRadiation);

            var minimumPhotosyntheticallyActiveRadiation = defaultMeasurement(plot, measurement)
                    .withMeasurement("minimumPhotosyntheticallyActiveRadiation",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getMinimumPhotosyntheticallyActiveRadiation()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "minimumPhotosyntheticallyActiveRadiation"))
                    .build();
            deviceMeasurementIntegrationService.persist(minimumPhotosyntheticallyActiveRadiation);

            var maximumPhotosyntheticallyActiveRadiation = defaultMeasurement(plot, measurement)
                    .withMeasurement("maximumPhotosyntheticallyActiveRadiation",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getMaximumPhotosyntheticallyActiveRadiation()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "maximumPhotosyntheticallyActiveRadiation"))
                    .build();
            deviceMeasurementIntegrationService.persist(maximumPhotosyntheticallyActiveRadiation);

            var dewPoint = defaultMeasurement(plot, measurement)
                    .withMeasurement("dewPoint",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getDewPoint()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "dewPoint"))
                    .build();
            deviceMeasurementIntegrationService.persist(dewPoint);

            var potentialEvapotranspiration = defaultMeasurement(plot, measurement)
                    .withMeasurement("potentialEvapotranspiration",
                            FiwareTypes.TEXT.getKey(),
                            String.valueOf(measurement.getMeasurementValues().getPotentialEvapotranspiration()),
                            measurement.getTimestamp(),
                            new DeviceMeasurementBuilder.MetadataEntry(FiwareMetadataTypes.CONTROLLED_PROPERTY.getKey(),
                                    FiwareTypes.TEXT.getKey(),
                                    "potentialEvapotranspiration"))
                    .build();
            deviceMeasurementIntegrationService.persist(potentialEvapotranspiration);
        });
    }

    private DeviceMeasurementBuilder defaultMeasurement(Plot plot, Measurement measurement) {
        log.debug("Persisting probe data for probe: {}", plot);
        log.debug("Persisting measurement data: {}", measurement);
        return new DeviceMeasurementBuilder()
                .withId(getManufacturerConfiguration().fiwarePrefix() + plot.getId())
                .withType(MeasurementType.WEENAT_SENSOR.name())
                .withLocation(plot.getLatitude(), plot.getLongitude());
    }

    private WeenatConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().weenat();
    }

}
