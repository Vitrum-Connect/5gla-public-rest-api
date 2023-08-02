package de.app.fivegla.integration.farm21.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Sensor model.
 */
@Getter
@Setter
public class Sensor {

    private int id;

    @JsonProperty("sensor_version_id")
    private int sensorVersionId;

    private String name;

    double latitude;

    double longitude;

    private String health;

    private String battery;

    @JsonProperty("last_reading")
    private Instant lastReading;

    private Field field;

    private CalibrationProfile calibrationProfile;
}
