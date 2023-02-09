package de.app.fivegla.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


/**
 * The response for retrieving sensor data.
 */
@Getter
@Builder
@Schema(description = "The response for retrieving sensor data.")
public class SensorDataResponse {

    /**
     * The sensor data.
     */
    public List<SensorDataDto> sensorData;

}
