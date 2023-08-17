package de.app.fivegla.integration.sentek;

import de.app.fivegla.SpringBootIntegrationTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SentekSensorIntegrationServiceTest extends SpringBootIntegrationTestBase {

    @Autowired
    private SentekSensorIntegrationService sentekSensorIntegrationService;

    @Test
    void givenValidXmlInputWhenParsingThenTheResultShouldBeValid() {
        try (var xmlAsStream = this.getClass().getResourceAsStream("/sentek-sensors.xml")) {
            assert xmlAsStream != null;
            var xml = new String(xmlAsStream.readAllBytes());
            var user = sentekSensorIntegrationService.parse(xml);
            assertThat(user).isNotNull();
            assertThat(user.getLoggers()).hasSize(5);
            //  <Logger id="46186" name="Ostfalia001" logger_id="Ostfalia001" latitude="0.000000" longitude="0.000000">
            var logger = user.getLoggers().get(0);
            assertThat(logger).isNotNull();
            assertThat(logger.getId()).isEqualTo(46186);
            assertThat(logger.getName()).isEqualTo("Ostfalia001");
            assertThat(logger.getLoggerId()).isEqualTo("Ostfalia001");
            assertThat(logger.getLatitude()).isEqualTo(0.000000);
            assertThat(logger.getLongitude()).isEqualTo(0.000000);
            //  <Site name="Default">
            assertThat(logger.getSite()).isNotNull();
            assertThat(logger.getSite().getName()).isEqualTo("Default");
            // <Probe name="P1">
            assertThat(logger.getSite().getProbe().getName()).isEqualTo("P1");
            assertThat(logger.getSite().getProbe().getSensors()).hasSize(20);
            // <Sensor name="V1" depth_cm="0" type="Voltage" description="Probe Supply" unit="V" minimum="0" maximum="50" invalid="-1" />
            var sensor = logger.getSite().getProbe().getSensors().get(0);
            assertThat(sensor).isNotNull();
            assertThat(sensor.getName()).isEqualTo("V1");
            assertThat(sensor.getDepth_cm()).isEqualTo(0);
            assertThat(sensor.getType()).isEqualTo("Voltage");
            assertThat(sensor.getDescription()).isEqualTo("Probe Supply");
            assertThat(sensor.getUnit()).isEqualTo("V");
            assertThat(sensor.getMinimum()).isEqualTo(0);
            assertThat(sensor.getMaximum()).isEqualTo(50);
            assertThat(sensor.getInvalid()).isEqualTo(-1);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

}