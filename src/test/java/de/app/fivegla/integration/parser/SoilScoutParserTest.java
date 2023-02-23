package de.app.fivegla.integration.parser;

import de.app.fivegla.integration.soilscout.parser.SoilScoutParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SoilScoutParserTest {

    private String sensorData = "{\n" +
            "  \"timestamp\": \"2020-05-01T12:00:00.000Z\",\n" +
            "  \"device\": 1,\n" +
            "  \"temperature\": 20.0,\n" +
            "  \"moisture\": 0.5,\n" +
            "  \"conductivity\": 0.5,\n" +
            "  \"dielectricity\": 0.5,\n" +
            "  \"site\": 1,\n" +
            "  \"salinity\": 0.5,\n" +
            "  \"field_capacity\": 0.5,\n" +
            "  \"wilting_point\": 0.5,\n" +
            "  \"water_balance\": 0.5\n" +
            "}";

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