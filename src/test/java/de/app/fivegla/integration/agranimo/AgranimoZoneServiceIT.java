package de.app.fivegla.integration.agranimo;

import de.app.fivegla.SpringBootIntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AgranimoZoneServiceIT extends SpringBootIntegrationTestBase {

    @Autowired
    private AgranimoZoneService agranimoZoneService;

    @Test
    void givenValidCredentialsWhenFetchingTheZonesThenThereShouldBeAtLeast4Zones() {
        var zones = agranimoZoneService.fetchZones();
        assertThat(zones).isNotEmpty();
        assertThat(zones.size()).isEqualTo(4);
    }
}