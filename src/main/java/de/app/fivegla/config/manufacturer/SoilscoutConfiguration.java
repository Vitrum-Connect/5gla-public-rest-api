package de.app.fivegla.config.manufacturer;

import de.app.fivegla.api.Manufacturer;

/**
 * Represents the configuration for Soilscout.
 */
public record SoilscoutConfiguration(
        boolean enabled,
        Manufacturer manufacturer,
        String fiwarePrefix,
        String username,
        String password,
        String url
) implements CommonManufacturerConfiguration {
}