package de.app.fivegla.integration.agranimo;

import de.app.fivegla.SpringBootIntegrationTestBase;
import de.app.fivegla.integration.agranimo.model.Zone;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AgranimoSoilMoistureServiceIT extends SpringBootIntegrationTestBase {

    @Autowired
    private AgranimoSoilMoistureIntegrationService soilMoistureService;
    @Autowired
    private AgranimoZoneService agranimoZoneService;

    private final Instant until = Instant.ofEpochSecond(1662617200);

    @Test
    void givenValidCredentialsWhenFetchingWaterContentThenThereShouldBeEntriesForTheZone() {
        List<Zone> zones = agranimoZoneService.fetchZones();
        zones.forEach(zone ->
        {
            var soilMoistures = soilMoistureService.fetchWaterContent(zone, until);
            assertThat(soilMoistures).isNotEmpty();
            assertThat(soilMoistures.size()).isGreaterThan(0);
        });
    }
}