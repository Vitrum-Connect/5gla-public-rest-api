package de.app.fivegla.integration.parser;

import com.google.gson.Gson;
import de.app.fivegla.integration.data.SoilScoutData;
import org.springframework.stereotype.Component;

/**
 * Parser for soil scout data.
 */
@Component
public class SoilScoutParser {

    /**
     * Parses the sensor data.
     *
     * @param sensorData the sensor data
     * @return the soil scout data
     */
    public SoilScoutData parse(String sensorData) {
        return new Gson().fromJson(sensorData, SoilScoutData.class);
    }

}
