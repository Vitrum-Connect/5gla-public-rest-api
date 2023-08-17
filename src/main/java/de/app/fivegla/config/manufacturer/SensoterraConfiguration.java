package de.app.fivegla.config.manufacturer;

import de.app.fivegla.api.Manufacturer;

/**
 * Represents the configuration for Sensoterra.
 */
public record SensoterraConfiguration(
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
