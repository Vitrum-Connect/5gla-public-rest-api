package de.app.fivegla.api.enums;

import lombok.Getter;

/**
 * Represents the type of measurement.
 */
@Getter
public enum MeasurementType {
    SENTEK_SENSOR("SentekSensor"),
    WEENAT_SENSOR("WeenatSensor"),
    AGVOLUTION_SENSOR("AgvolutionSensor"),
    MICASENSE_IMAGE("MicaseSenseImage"),
    SENSOTERRA_SENSOR("SensoterraSensor"),
    FARM21_SENSOR("Farm21Sensor"),
    AGRANIMO_SENSOR("AgranimoSensor"),
    SOILSCOUT_SENSOR("SoilScoutSensor"),
    DEVICE_POSITION("DevicePosition"),
    UNKNOWN("Unknown");

    private final String key;

    MeasurementType(String key) {
        this.key = key;
    }
}
