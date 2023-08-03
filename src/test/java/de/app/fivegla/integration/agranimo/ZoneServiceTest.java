package de.app.fivegla.integration.agranimo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ZoneServiceTest {

    @Autowired
    private ZoneService zoneService;

    @Test
    void givenValidCredentialsWhenFetchingTheZonesThenThereShouldBeAtLeast4Zones() {
        var zones = zoneService.fetchZones();
        Assertions.assertThat(zones).isNotEmpty();
        Assertions.assertThat(zones.size()).isEqualTo(4);
    }
}