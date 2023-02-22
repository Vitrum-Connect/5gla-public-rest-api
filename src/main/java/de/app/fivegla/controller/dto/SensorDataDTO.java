package de.app.fivegla.controller.dto;

import de.app.fivegla.domain.SensorType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * The sensor data.
 */
@Getter
@Setter
@Schema(description = "The sensor data.")
public class SensorDataDTO {

    /**
     * The sensor id.
     */
    @Schema(description = "The sensor id.")
    private String sensorId;

    /**
     * The sensor name.
     */
    @Schema(description = "The sensor name.")
    private String sensorName;

    /**
     * The geolocation.
     */
    @Schema(description = "The geolocation.")
    private GeoLocationDTO geoLocation;

    /**
     * The sensor type.
     */
    @Schema(description = "The sensor type.")
    private SensorType sensorType;

}
