package de.app.fivegla.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Sensor master data.
 */
@Getter
@Setter
@Builder
public class SensorMasterData {

    /**
     * The sensor id.
     */
    private String sensorId;

}
