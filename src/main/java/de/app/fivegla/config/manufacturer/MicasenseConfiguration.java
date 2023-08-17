package de.app.fivegla.config.manufacturer;

import de.app.fivegla.api.Manufacturer;

/**
 * Represents the configuration for the Micasense sensor.
 */
public record MicasenseConfiguration(
        boolean enabled,
        Manufacturer manufacturer,
        String fiwareDeviceIdPrefix,
        String fiwareDeviceMeasurementIdPrefix,
        String imagePathBaseUrl
) implements CommonManufacturerConfiguration {
    @Override
    public String manufacturerName() {
        return manufacturer.name().toLowerCase();
    }
}