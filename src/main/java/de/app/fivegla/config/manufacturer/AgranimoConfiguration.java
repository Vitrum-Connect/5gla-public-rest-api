package de.app.fivegla.config.manufacturer;

import de.app.fivegla.api.Manufacturer;

/**
 * Represents the configuration for the Agranimo module.
 * This class provides the necessary properties to configure the Agranimo module,
 * such as enablement status, manufacturer, device ID prefix, measurement ID prefix,
 * username, password, and URL.
 * The AgranimoConfiguration class also implements the CommonManufacturerConfiguration
 * interface which provides the manufacturerName() method to retrieve the lowercase
 * name of the manufacturer.
 */
public record AgranimoConfiguration(
        boolean enabled,
        Manufacturer manufacturer,
        String fiwarePrefix,
        String username,
        String password,
        String url
) implements CommonManufacturerConfiguration {
}
