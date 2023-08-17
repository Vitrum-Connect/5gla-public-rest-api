package de.app.fivegla.config.manufacturer;

import de.app.fivegla.api.Manufacturer;

public record SentekConfiguration(
        boolean enabled,
        Manufacturer manufacturer,
        String fiwareDeviceIdPrefix,
        String fiwareDeviceMeasurementIdPrefix,
        String url,
        String apiToken
) implements CommonManufacturerConfiguration {
    @Override
    public String manufacturerName() {
        return manufacturer.name().toLowerCase();
    }
}
