package de.app.fivegla.config.manufacturer;

import de.app.fivegla.api.Manufacturer;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the configuration for Agvolution.
 *
 * Agvolution is a class used to configure Agvolution data sources.
 *
 * The configuration includes information such as whether Agvolution is enabled,
 * the manufacturer, FIWARE device ID prefix, FIWARE device measurement ID prefix,
 * username, password, and URL.
 *
 * This configuration class implements the CommonManufacturerConfiguration interface
 * and provides an implementation for the manufacturerName() method.
 *
 * @see CommonManufacturerConfiguration
 */
public record AgvolutionConfiguration(
        boolean enabled,
        Manufacturer manufacturer,
        String fiwareDeviceIdPrefix,
        String fiwareDeviceMeasurementIdPrefix,
        String username,
        String password,
        String url
) implements CommonManufacturerConfiguration {
    @Override
    public String manufacturerName() {
        return manufacturer.name().toLowerCase();
    }
}
