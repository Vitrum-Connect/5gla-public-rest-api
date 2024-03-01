package de.app.fivegla.config.manufacturer;

import de.app.fivegla.api.Manufacturer;

/**
 * Represents the configuration for a Sentek device.
 * It includes information such as whether the device is enabled, the manufacturer,
 * the FIWARE device ID prefix, the device URL, and the API token.
 * This class implements the {@link CommonManufacturerConfiguration} interface and provides the ability
 * to retrieve the manufacturer name in lower case.
 */
public record SentekConfiguration(
        boolean enabled,
        Manufacturer manufacturer,
        String fiwarePrefix,
        String url,
        String apiToken
) implements CommonManufacturerConfiguration {
}
