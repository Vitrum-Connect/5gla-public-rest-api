package de.app.fivegla.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Request to publishing sensor data.
 */
@Getter
@Setter
@Schema(description = "Request for publishing sensor data.")
public class PublishSensorDataRequest {

    /**
     * The sensor id.
     */
    @Schema(description = "The sensor id.")
    private String sensorId;

    /**
     * The sensor data.
     */
    @Schema(description = "The Base64 encoded sensor data.")
    private String base64EncodedSensorData;

}
