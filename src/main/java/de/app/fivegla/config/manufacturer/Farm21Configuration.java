package de.app.fivegla.config.manufacturer;

import de.app.fivegla.api.Manufacturer;

/**
 * Represents the configuration for a Farm21 device.
 * It includes information such as whether the device is enabled, the manufacturer,
 * the device ID prefix, measurement ID prefix, authentication credentials, and API related information.
 * This class implements the {@link CommonManufacturerConfiguration} interface and provides the ability
 * to retrieve the manufacturer name in lower case.
 */
public record Farm21Configuration(
        boolean enabled,
        Manufacturer manufacturer,
        String fiwarePrefix,
        String username,
        String password,
        String url,
        String apiToken
) implements CommonManufacturerConfiguration {
}
