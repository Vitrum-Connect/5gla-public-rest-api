package de.app.fivegla.integration.weenat;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.app.fivegla.integration.weenat.model.MeasurementValues;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class ParseWeenatMeasurementResponseJsonTest {

    @SuppressWarnings("FieldCanBeLocal")
    private final String JSON = """
            {
                "1692882000": {
                    "FF": 4.5,
                    "RR": 0,
                    "SSI": 646,
                    "T": 27.8,
                    "T_DEW": 12.6,
                    "U": 40
                },
                "1692885600": {
                    "FF": 3.5,
                    "RR": 0,
                    "SSI": 551,
                    "T": 27.8,
                    "T_DEW": 12.6,
                    "U": 40
                },
                "1692889200": {
                    "FF": 3.9,
                    "RR": 0,
                    "SSI": 209,
                    "T": 28,
                    "T_DEW": 12.8,
                    "U": 39
                },
                "1692892800": {
                    "FF": 4.3,
                    "RR": 0,
                    "SSI": 84,
                    "T": 27.4,
                    "T_DEW": 12.4,
                    "U": 40
                },
                "1692896400": {
                    "FF": 3.2,
                    "RR": 0,
                    "SSI": 33,
                    "T": 26.6,
                    "T_DEW": 12.6,
                    "U": 43
                },
                "1692900000": {
                    "FF": 3.7,
                    "RR": 0,
                    "SSI": 0,
                    "T": 25.6,
                    "T_DEW": 12.4,
                    "U": 44
                },
                "1692903600": {
                    "FF": 2.8,
                    "RR": 0,
                    "SSI": 0,
                    "T": 23.8,
                    "T_DEW": 13.2,
                    "U": 51
                },
                "1692907200": {
                    "FF": 3.5,
                    "RR": 0.3,
                    "SSI": 0,
                    "T": 22.8,
                    "T_DEW": 15.6,
                    "U": 64
                },
                "1692910800": {
                    "FF": 5.4,
                    "RR": 1,
                    "SSI": 0,
                    "T": 21.2,
                    "T_DEW": 18.4,
                    "U": 85
                },
                "1692914400": {
                    "FF": 3.5,
                    "RR": 0,
                    "SSI": 0,
                    "T": 18,
                    "T_DEW": 16,
                    "U": 94
                },
                "1692918000": {
                    "FF": 3.7,
                    "RR": 0,
                    "SSI": 0,
                    "T": 17.6,
                    "T_DEW": 17,
                    "U": 95
                },
                "1692921600": {
                    "FF": 3.7,
                    "RR": 0,
                    "SSI": 0,
                    "T": 18.4,
                    "T_DEW": 17,
                    "U": 95
                },
                "1692925200": {
                    "FF": 3.5,
                    "RR": 0,
                    "SSI": 0,
                    "T": 18.2,
                    "T_DEW": 17,
                    "U": 97
                },
                "1692928800": {
                    "FF": 4.3,
                    "RR": 0,
                    "SSI": 0,
                    "T": 18,
                    "T_DEW": 17,
                    "U": 97
                },
                "1692932400": {
                    "FF": 5.6,
                    "RR": 0,
                    "SSI": 0,
                    "T": 18,
                    "T_DEW": 16,
                    "U": 96
                },
                "1692936000": {
                    "FF": 5,
                    "RR": 0,
                    "SSI": 0,
                    "T": 18,
                    "T_DEW": 16,
                    "U": 97
                },
                "1692939600": {
                    "FF": 5,
                    "RR": 0,
                    "SSI": 3,
                    "T": 17.8,
                    "T_DEW": 16,
                    "U": 96
                },
                "1692943200": {
                    "FF": 4.3,
                    "RR": 0,
                    "SSI": 40,
                    "T": 19.2,
                    "T_DEW": 16,
                    "U": 91
                },
                "1692946800": {
                    "FF": 5.2,
                    "RR": 0,
                    "SSI": 117,
                    "T": 20.8,
                    "T_DEW": 17,
                    "U": 85
                },
                "1692950400": {
                    "FF": 6,
                    "RR": 0,
                    "SSI": 97,
                    "T": 22.4,
                    "T_DEW": 18,
                    "U": 78
                },
                "1692954000": {
                    "FF": 7.3,
                    "RR": 0,
                    "SSI": 44,
                    "T": 23.8,
                    "T_DEW": 18,
                    "U": 72
                },
                "1692957600": {
                    "FF": 7.3,
                    "RR": 0,
                    "SSI": 10,
                    "T": 24.4,
                    "T_DEW": 18,
                    "U": 68
                },
                "1692961200": {
                    "FF": 7.8,
                    "RR": 0,
                    "SSI": 2,
                    "T": 24,
                    "T_DEW": 18,
                    "U": 69
                },
                "1692964800": {
                    "FF": 7.6,
                    "RR": 0,
                    "SSI": 10,
                    "T": 22.6,
                    "T_DEW": 17,
                    "U": 76
                }
            }
                        """;

    @Test
    @SuppressWarnings("unchecked")
    void givenValidJsonWhenParseThenTheResponseShouldBeParsed() throws Throwable {
        var objectMapper = new ObjectMapper();
        var type = objectMapper.getTypeFactory().constructMapType(HashMap.class, Long.class, MeasurementValues.class);
        var mapOfMeasurementEntries = (HashMap<Long, MeasurementValues>) objectMapper.readValue(JSON, type);
        assertThat(mapOfMeasurementEntries).isNotNull();
        assertThat(mapOfMeasurementEntries).isNotEmpty();
        assertThat(mapOfMeasurementEntries).hasSize(24);
        assertThat(mapOfMeasurementEntries.get(1692882000L)).isNotNull();
        assertThat(mapOfMeasurementEntries.get(1692882000L).getTemperature()).isEqualTo("27.8");
        assertThat(mapOfMeasurementEntries.get(1692882000L).getRelativeHumidity()).isEqualTo("40");
        assertThat(mapOfMeasurementEntries.get(1692882000L).getCumulativeRainfall()).isEqualTo("0");
        assertThat(mapOfMeasurementEntries.get(1692882000L).getWindSpeed()).isEqualTo("4.5");
        assertThat(mapOfMeasurementEntries.get(1692882000L).getWindGustSpeed()).isNull();
        assertThat(mapOfMeasurementEntries.get(1692882000L).getSoilTemperature15()).isNull();
    }

}
