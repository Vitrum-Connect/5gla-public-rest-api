package de.app.fivegla.persistence.root;

import de.app.fivegla.domain.SensorMasterData;

import java.util.ArrayList;
import java.util.List;

/**
 * Cache root object.
 */
public class SensorMasterDataRoot {

    /**
     * Sensor master data.
     */
    private List<SensorMasterData> sensorMasterData;

    /**
     * Get the message cache.
     *
     * @return THe message cache.
     */
    public List<SensorMasterData> getSensorMasterData() {
        if (sensorMasterData == null) {
            sensorMasterData = new ArrayList<>();
        }
        return sensorMasterData;
    }

}
