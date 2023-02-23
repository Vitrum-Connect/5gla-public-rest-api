package de.app.fivegla.integration.soilscout;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SoilScoutIntegrationServiceTest {

    @Autowired
    private SoilScoutIntegrationService soilScoutIntegrationService;

    @Test
    void fetchAndRegisterSensors() {
        var soilScoutSensors = soilScoutIntegrationService.fetchSensors();
        Assertions.assertNotNull(soilScoutSensors);
        Assertions.assertFalse(soilScoutSensors.isEmpty());
    }

}