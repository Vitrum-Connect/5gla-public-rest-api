package de.app.fivegla.integration.soilscout.dto.response;

import de.app.fivegla.integration.soilscout.model.SensorData;
import lombok.Getter;
import lombok.Setter;

/**
 * Soil scout sensor data response.
 */
@Getter
@Setter
public class MeasurementResponse {

    private String next;
    private String previous;
    private SensorData[] results;

}
