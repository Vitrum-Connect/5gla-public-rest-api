package de.app.fivegla.api.enums;

import lombok.Getter;

/**
 * Represents the type of measurement.
 */
@Getter
public enum EntityType {
    SENTEK_SENSOR("SentekSensor"),
    WEENAT_SENSOR("WeenatSensor"),
    AGVOLUTION_SENSOR("AgvolutionSensor"),
    IMAGE("Image"),
    STATIONARY_IMAGE("StationaryImage"),
    SENSOTERRA_SENSOR("SensoterraSensor"),
    FARM21_SENSOR("Farm21Sensor"),
    AGRANIMO_SENSOR("AgranimoSensor"),
    SOILSCOUT_SENSOR("SoilScoutSensor"),
    DEVICE_POSITION("DevicePosition"),
    AGRI_CROP("AgriCrop"),
    OPEN_WEATHER_MAP("OpenWeatherMap"),
    MANUAL_PRECIPITATION_EVENT("ManualPrecipitationEvent"),
    UNKNOWN("Unknown");

    private final String key;

    EntityType(String key) {
        this.key = key;
    }
}
