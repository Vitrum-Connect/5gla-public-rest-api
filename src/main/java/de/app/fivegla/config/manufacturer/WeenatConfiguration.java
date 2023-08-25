package de.app.fivegla.config.manufacturer;

import de.app.fivegla.api.Manufacturer;

/**
 * Represents the configuration settings for the Weenat device.
 */
public record WeenatConfiguration(
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
