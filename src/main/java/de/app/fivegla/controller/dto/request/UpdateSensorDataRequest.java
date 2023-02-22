package de.app.fivegla.controller.dto.request;

import de.app.fivegla.controller.dto.GeoLocationDTO;
import de.app.fivegla.domain.SensorType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Request for creating sensor data.
 */
@Getter
@Setter
@Schema(description = "Request for creating sensor data.")
public class UpdateSensorDataRequest {

    /**
     * The sensor id.
     */
    @Schema(description = "The sensor id.")
    @NotBlank
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
