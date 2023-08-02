package de.app.fivegla.integration.soilscout;

import de.app.fivegla.api.exceptions.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SoilScoutFarm21SensorIntegrationServiceIT {

    @Autowired
    private SoilScoutSensorIntegrationService soilScoutSensorIntegrationService;

    @Test
    void whenFetchingSensorsTheServiceShouldReturnSensors() {
        var sensors = soilScoutSensorIntegrationService.findAll();
        Assertions.assertNotNull(sensors);
        Assertions.assertFalse(sensors.isEmpty());
    }

    @Test
    void givenExistingSensorsWhenFetchingSingleSensorTheServiceShouldReturnTheSpecificSensor() {
        var sensors = soilScoutSensorIntegrationService.findAll();
        Assertions.assertNotNull(sensors);
        Assertions.assertFalse(sensors.isEmpty());
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
