package de.app.fivegla.integration.farm21.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Sensor data model.
 */
@Getter
@Setter
public class SensorData {

    @JsonProperty("soil_moisture_10")
    private double soilMoisture10;

    @JsonProperty("soil_moisture_20")
    private double soilMoisture20;

    @JsonProperty("soil_moisture_30")
    private double soilMoisture30;

    @JsonProperty("temp_neg_10")
    private double tempNeg10;

    private double humidity;

    @JsonProperty("temp_pos_10")
    private double tempPos10;

    private double latitude;

    private double longitude;

    private double battery;

    @JsonProperty("measured_at")
    private Instant measuredAt;

    private int id;

    @JsonProperty("soil_temperature")
    private double soilTemperature;

    @JsonProperty("air_temperature")
    private double airTemperature;
}
