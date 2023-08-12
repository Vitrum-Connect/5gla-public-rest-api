package de.app.fivegla.integration.agranimo;

import de.app.fivegla.SpringBootIntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ZoneServiceIT extends SpringBootIntegrationTestBase {

    @Autowired
    private ZoneService zoneService;

    @Test
    void givenValidCredentialsWhenFetchingTheZonesThenThereShouldBeAtLeast4Zones() {
        var zones = zoneService.fetchZones();
        assertThat(zones).isNotEmpty();
        assertThat(zones.size()).isEqualTo(4);
    }
}