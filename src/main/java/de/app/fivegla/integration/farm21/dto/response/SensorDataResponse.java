package de.app.fivegla.integration.farm21.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.app.fivegla.integration.farm21.model.SensorData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Sensor data response.
 */
@Getter
@Setter
public class SensorDataResponse {

    /**
     * The sensor id.
     */
    @JsonProperty("id")
    private String sensorId;

    /**
     * The sensor name.
     */
    private String name;

    /**
     * The sensor data.
     */
    @JsonProperty("readings")
    private List<SensorData> sensorData;

}
