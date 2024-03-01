package de.app.fivegla.config.manufacturer;

import de.app.fivegla.api.Manufacturer;

public record SentekConfiguration(
        boolean enabled,
        Manufacturer manufacturer,
        String fiwarePrefix,
        String url,
        String apiToken
) implements CommonManufacturerConfiguration {
}
