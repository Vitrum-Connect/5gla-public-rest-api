package de.app.fivegla.integration.parser;

import de.app.fivegla.integration.soilscout.parser.SoilScoutParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SoilScoutParserTest {

    @SuppressWarnings("FieldCanBeLocal")
    private final String sensorData = """
            {
              "timestamp": "2020-05-01T12:00:00.000Z",
              "device": 1,
              "temperature": 20.0,
              "moisture": 0.5,
              "conductivity": 0.5,
              "dielectricity": 0.5,
              "site": 1,
              "salinity": 0.5,
              "field_capacity": 0.5,
              "wilting_point": 0.5,
              "water_balance": 0.5
            }""";

    @Test
    void givenValidSensorDataWhenParsingTheDataTheResultShouldBeValid() {
        var soilScoutParser = new SoilScoutParser();
        var soilScoutData = soilScoutParser.parse(sensorData);
        Assertions.assertNotNull(soilScoutData);
        Assertions.assertEquals(1, soilScoutData.getDevice());
        Assertions.assertEquals(20.0, soilScoutData.getTemperature());
        Assertions.assertEquals(0.5, soilScoutData.getMoisture());
        Assertions.assertEquals(0.5, soilScoutData.getConductivity());
        Assertions.assertEquals(0.5, soilScoutData.getDielectricity());
        Assertions.assertEquals(1, soilScoutData.getSite());
        Assertions.assertEquals(0.5, soilScoutData.getSalinity());
        Assertions.assertEquals(0.5, soilScoutData.getFieldCapacity());
        Assertions.assertEquals(0.5, soilScoutData.getWiltingPoint());
        Assertions.assertEquals(0.5, soilScoutData.getWaterBalance());
    }

}