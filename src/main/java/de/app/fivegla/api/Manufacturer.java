package de.app.fivegla.api;

import lombok.Getter;

import java.util.Arrays;

/**
 * Sensor manufacturers.
 */
@Getter
public enum Manufacturer {
    SOIL_SCOUT(ManufacturerType.SOIL_SCOUT, "urn:5gla:soil-scout-sensor:", "urn:5gla:soil-scout-sensor:data:"),
    AGRANIMO(ManufacturerType.AGRANIMO, "urn:5gla:agranimo-sensor:", "urn:5gla:agranimo-sensor:data:"),
    FARM21(ManufacturerType.FARM21, "urn:5gla:farm21-sensor:", "urn:5gla:farm21-sensor:data:"),
    MICA_SENSE(ManufacturerType.MICA_SENSE, "urn:5gla:mica-sense-drone:", "urn:5gla:mica-sense-drone:data:"),
    AGVOLUTION(ManufacturerType.AGVOLUTION, "urn:5gla:agvolution-sensor:", "urn:5gla:agvolution-sensor:data:"),
    SENSOTERRA(ManufacturerType.SENSOTERRA, "urn:5gla:sensoterra-sensor:", "urn:5gla:sensoterra-sensor:data:");

    private final ManufacturerType manufacturerType;
    private final String fiwareDeviceIdPrefix;
    private final String fiwareSensorDataIdPrefix;

    Manufacturer(ManufacturerType manufacturerType,
                 String fiwareDeviceIdPrefix,
                 String fiwareSensorDataIdPrefix) {
        this.manufacturerType = manufacturerType;
        this.fiwareDeviceIdPrefix = fiwareDeviceIdPrefix;
        this.fiwareSensorDataIdPrefix = fiwareSensorDataIdPrefix;
    }

    /**
     * Returns the manufacturer for the given manufacturer type.
     *
     * @param manufacturerType the manufacturerType
     * @return the manufacturer
     */
    public static Manufacturer fromManufacturerType(ManufacturerType manufacturerType) {
        return Arrays.stream(Manufacturer.values()).filter(m -> m.manufacturerType == manufacturerType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown manufacturer type: " + manufacturerType));
    }

    /**
     * Returns the manufacturer for the given manufacturer type.
     *
     * @return the manufacturer.
     */
    public String key() {
        return name().toLowerCase();
    }
}
