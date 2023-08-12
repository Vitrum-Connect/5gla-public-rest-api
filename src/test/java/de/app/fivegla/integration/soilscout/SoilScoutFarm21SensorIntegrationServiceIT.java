package de.app.fivegla.integration.soilscout;

import de.app.fivegla.SpringBootIntegrationTestBase;
import de.app.fivegla.api.exceptions.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SoilScoutFarm21SensorIntegrationServiceIT extends SpringBootIntegrationTestBase {

    @Autowired
    private SoilScoutSensorIntegrationService soilScoutSensorIntegrationService;

    @Test
    void whenFetchingSensorsTheServiceShouldReturnSensors() {
        var sensors = soilScoutSensorIntegrationService.findAll();
        assertThat(sensors).isNotNull();
        assertThat(sensors).isNotEmpty();
    }

    @Test
    void givenExistingSensorsWhenFetchingSingleSensorTheServiceShouldReturnTheSpecificSensor() {
        var sensors = soilScoutSensorIntegrationService.findAll();
        assertThat(sensors).isNotNull();
        assertThat(sensors).isNotEmpty();
        sensors.forEach(soilScoutSensor -> {
            var sensor = soilScoutSensorIntegrationService.find(soilScoutSensor.getId());
            Assertions.assertEquals(soilScoutSensor.getId(), sensor.getId());
        });
    }

    @Test
    void givenMissingSensorWhenFetchingSingleSensorTheServiceShouldReturn() {
        Assertions.assertThrows(BusinessException.class, () -> soilScoutSensorIntegrationService.find(42));
    }

}
