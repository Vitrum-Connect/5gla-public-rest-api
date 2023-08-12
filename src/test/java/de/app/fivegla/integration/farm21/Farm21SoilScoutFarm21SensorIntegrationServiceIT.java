package de.app.fivegla.integration.farm21;

import de.app.fivegla.SpringBootIntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Farm21SoilScoutFarm21SensorIntegrationServiceIT extends SpringBootIntegrationTestBase {

    @Autowired
    private Farm21SensorIntegrationService farm21SensorIntegrationService;

    @Test
    void givenExistingSensorsWhenSearchingViaApiTheServiceShouldReturnAllOfThem() {
        var sensors = farm21SensorIntegrationService.findAll();
        assertThat(sensors).isNotNull();
        assertThat(sensors).isNotEmpty();
    }

}