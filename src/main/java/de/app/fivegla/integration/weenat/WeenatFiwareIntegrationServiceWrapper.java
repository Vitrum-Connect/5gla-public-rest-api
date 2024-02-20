package de.app.fivegla.integration.weenat;


import de.app.fivegla.api.FiwareDeviceId;
import de.app.fivegla.api.Format;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.WeenatConfiguration;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.Location;
import de.app.fivegla.integration.weenat.model.Measurement;
import de.app.fivegla.integration.weenat.model.Measurements;
import de.app.fivegla.integration.weenat.model.Plot;
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
public class WeenatFiwareIntegrationServiceWrapper {
    private final DeviceMeasurementIntegrationService deviceMeasurementIntegrationService;
    private final ApplicationConfiguration applicationConfiguration;

    public void persist(Plot plot, Measurements measurements) {
        measurements.getMeasurements().forEach(measurement -> {
            log.info("Persisting measurement for measurement: {}", measurement);
            var deviceMeasurement = createDeviceMeasurement(plot, measurement);

            var temperature = deviceMeasurement.numValue(measurement.getMeasurementValues().getTemperature())
                    .unit("°C")
                    .build();
            if (temperature != null) {
                log.info("Skipping measurement");
            } else {
                deviceMeasurementIntegrationService.persist(temperature);
            }

            var relativeHumidity = deviceMeasurement.numValue(measurement.getMeasurementValues().getRelativeHumidity())
                    .unit("%")
                    .build();
            if (relativeHumidity != null) {
                deviceMeasurementIntegrationService.persist(relativeHumidity);
            }

            var cumulativeRainfall = deviceMeasurement.numValue(measurement.getMeasurementValues().getCumulativeRainfall())
                    .unit("mm")
                    .build();
            if (cumulativeRainfall != null) {
                deviceMeasurementIntegrationService.persist(cumulativeRainfall);
            }

            var windSpeed = deviceMeasurement.numValue(measurement.getMeasurementValues().getWindSpeed())
                    .unit("km/h")
                    .build();
            if (windSpeed != null) {
                deviceMeasurementIntegrationService.persist(windSpeed);
            }

            var windGustSpeed = deviceMeasurement.numValue(measurement.getMeasurementValues().getWindGustSpeed())
                    .unit("km/h")
                    .build();
            if (windGustSpeed != null) {
                deviceMeasurementIntegrationService.persist(windGustSpeed);
            }

            var soiltemperature = deviceMeasurement.numValue(measurement.getMeasurementValues().getSoilTemperature())
                    .unit("°C")
                    .build();
            if (soiltemperature != null) {
                deviceMeasurementIntegrationService.persist(soiltemperature);
            }

            var soiltemperature15 = deviceMeasurement.numValue(measurement.getMeasurementValues().getSoilTemperature15())
                    .unit("°C")
                    .build();
            if (soiltemperature15 != null) {
                deviceMeasurementIntegrationService.persist(soiltemperature15);
            }

            var soiltemperature30 = deviceMeasurement.numValue(measurement.getMeasurementValues().getSoilTemperature30())
                    .unit("°C")
                    .build();
            if (soiltemperature30 != null) {
                deviceMeasurementIntegrationService.persist(soiltemperature30);
            }

            var soiltemperature60 = deviceMeasurement.numValue(measurement.getMeasurementValues().getSoilTemperature60())
                    .unit("°C")
                    .build();
            if (soiltemperature60 != null) {
                deviceMeasurementIntegrationService.persist(soiltemperature60);
            }

            var soilWaterPotential15 = deviceMeasurement.numValue(measurement.getMeasurementValues().getSoilWaterPotential15())
                    .unit("kPa")
                    .build();
            if (soilWaterPotential15 != null) {
                deviceMeasurementIntegrationService.persist(soilWaterPotential15);
            }

            var soilWaterPotential30 = deviceMeasurement.numValue(measurement.getMeasurementValues().getSoilWaterPotential30())
                    .unit("kPa")
                    .build();
            if (soilWaterPotential30 != null) {
                deviceMeasurementIntegrationService.persist(soilWaterPotential30);
            }

            var soilWaterPotential60 = deviceMeasurement.numValue(measurement.getMeasurementValues().getSoilWaterPotential60())
                    .unit("kPa")
                    .build();
            if (soilWaterPotential60 != null) {
                deviceMeasurementIntegrationService.persist(soilWaterPotential60);
            }

            var dryTemperature = deviceMeasurement.numValue(measurement.getMeasurementValues().getDryTemperature())
                    .unit("°C")
                    .build();
            if (dryTemperature != null) {
                deviceMeasurementIntegrationService.persist(dryTemperature);
            }

            var wetTemperature = deviceMeasurement.numValue(measurement.getMeasurementValues().getWetTemperature())
                    .unit("°C")
                    .build();
            if (wetTemperature != null) {
                deviceMeasurementIntegrationService.persist(wetTemperature);
            }

            var leafWetnessDuration = deviceMeasurement.numValue(measurement.getMeasurementValues().getLeafWetnessDuration())
                    .unit("s")
                    .build();
            if (leafWetnessDuration != null) {
                deviceMeasurementIntegrationService.persist(leafWetnessDuration);
            }

            var leafWetnessVoltage = deviceMeasurement.numValue(measurement.getMeasurementValues().getLeafWetnessVoltage())
                    .unit("V")
                    .build();
            if (leafWetnessVoltage != null) {
                deviceMeasurementIntegrationService.persist(leafWetnessVoltage);
            }

            var solarIrridiance = deviceMeasurement.numValue(measurement.getMeasurementValues().getSolarIrradiance())
                    .unit("W/m²")
                    .build();
            if (solarIrridiance != null) {
                deviceMeasurementIntegrationService.persist(solarIrridiance);
            }

            var minimumSolarIrridiance = deviceMeasurement.numValue(measurement.getMeasurementValues().getMinSolarIrradiance())
                    .unit("W/m²")
                    .build();
            if (minimumSolarIrridiance != null) {
                deviceMeasurementIntegrationService.persist(minimumSolarIrridiance);
            }

            var maximumSolarIrridiance = deviceMeasurement.numValue(measurement.getMeasurementValues().getMaxSolarIrradiance())
                    .unit("W/m²")
                    .build();
            if (maximumSolarIrridiance != null) {
                deviceMeasurementIntegrationService.persist(maximumSolarIrridiance);
            }

            var photosyntheticallyActiveRadiation = deviceMeasurement.numValue(measurement.getMeasurementValues().getPhotosyntheticallyActiveRadiation())
                    .unit("µmol/s/m²")
                    .build();
            if (photosyntheticallyActiveRadiation != null) {
                deviceMeasurementIntegrationService.persist(photosyntheticallyActiveRadiation);
            }

            var minimumPhotosyntheticallyActiveRadiation = deviceMeasurement.numValue(measurement.getMeasurementValues().getMinimumPhotosyntheticallyActiveRadiation())
                    .unit("µmol/s/m²")
                    .build();
            if (minimumPhotosyntheticallyActiveRadiation != null) {
                deviceMeasurementIntegrationService.persist(minimumPhotosyntheticallyActiveRadiation);
            }

            var maximumPhotosyntheticallyActiveRadiation = deviceMeasurement.numValue(measurement.getMeasurementValues().getMaximumPhotosyntheticallyActiveRadiation())
                    .unit("µmol/s/m²")
                    .build();
            if (maximumPhotosyntheticallyActiveRadiation != null) {
                deviceMeasurementIntegrationService.persist(maximumPhotosyntheticallyActiveRadiation);
            }

            var dewPoint = deviceMeasurement.numValue(measurement.getMeasurementValues().getDewPoint())
                    .unit("°C")
                    .build();
            if (dewPoint != null) {
                deviceMeasurementIntegrationService.persist(dewPoint);
            }

            var potentialEvapotranspiration = deviceMeasurement.numValue(measurement.getMeasurementValues().getPotentialEvapotranspiration())
                    .unit("mm")
                    .build();
            if (potentialEvapotranspiration != null) {
                deviceMeasurementIntegrationService.persist(potentialEvapotranspiration);
            }
        });
    }

    private DeviceMeasurement.DeviceMeasurementBuilder createDeviceMeasurement(Plot plot, Measurement measurement) {
        log.debug("Persisting probe data for probe: {}", plot);
        log.debug("Persisting measurement data: {}", measurement);
        return DeviceMeasurement.builder()
                .id(FiwareDeviceId.create(getManufacturerConfiguration(), String.valueOf(plot.getId())))
                .manufacturerSpecificId(String.valueOf(plot.getId()))
                .dateObserved(Format.format(measurement.getTimestamp()))
                .location(Location.builder()
                        .coordinates(List.of(plot.getLatitude(), plot.getLongitude()))
                        .build())
                .controlledProperty("value");
    }

    private WeenatConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().weenat();
    }

}
