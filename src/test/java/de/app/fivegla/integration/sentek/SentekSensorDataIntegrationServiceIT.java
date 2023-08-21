package de.app.fivegla.integration.sentek;

import de.app.fivegla.SpringBootIntegrationTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SentekSensorDataIntegrationServiceIT extends SpringBootIntegrationTestBase {

    @Autowired
    private SentekSensorDataIntegrationService sentekSensorDataIntegrationService;

    @Test
    void givenValidCsvInputFileWhenParsingThenTheResultShouldBeValid() {
        try (var xmlAsStream = this.getClass().getResourceAsStream("/sentek-sensordata.csv")) {
            assert xmlAsStream != null;
            var csv = new String(xmlAsStream.readAllBytes());
            var readings = sentekSensorDataIntegrationService.parse(csv);
            assertThat(readings).isNotNull();
            assertThat(readings).hasSize(5783);
            var reading = readings.get(0);
            // 2023/03/08 10:00:00,12.563,-1,0,21.44,0.401477,20.82001,0.001182241,20.51001,0,21.14999,0,20.97,0.004979191,21.34,0,21.32999,0,20.98001,0,21.10999
            assertThat(reading).isNotNull();
            assertThat(reading.getV1()).isEqualTo(12.563);
            assertThat(reading.getV2()).isEqualTo(-1);
            assertThat(reading.getA1()).isEqualTo(0);
            assertThat(reading.getT1()).isEqualTo(21.44);
            assertThat(reading.getA2()).isEqualTo(0.401477);
            assertThat(reading.getT2()).isEqualTo(20.82001);
            assertThat(reading.getA3()).isEqualTo(0.001182241);
            assertThat(reading.getT3()).isEqualTo(20.51001);
            assertThat(reading.getA4()).isEqualTo(0);
            assertThat(reading.getT4()).isEqualTo(21.14999);
            assertThat(reading.getA5()).isEqualTo(0);
            assertThat(reading.getT5()).isEqualTo(20.97);
            assertThat(reading.getA6()).isEqualTo(0.004979191);
            assertThat(reading.getT6()).isEqualTo(21.34);
            assertThat(reading.getA7()).isEqualTo(0);
            assertThat(reading.getT7()).isEqualTo(21.32999);
            assertThat(reading.getA8()).isEqualTo(0);
            assertThat(reading.getT8()).isEqualTo(20.98001);
            assertThat(reading.getA9()).isEqualTo(0);
            assertThat(reading.getT9()).isEqualTo(21.10999);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void givenValidApiTokenWhenAccessingTheApiThenTheResultShouldBeValid() {
        var readings = sentekSensorDataIntegrationService.fetchAll("Ostfalia001", Instant.now().minus(14, ChronoUnit.DAYS));
        assertThat(readings).isNotNull();
        assertThat(readings).isNotEmpty();
    }

}