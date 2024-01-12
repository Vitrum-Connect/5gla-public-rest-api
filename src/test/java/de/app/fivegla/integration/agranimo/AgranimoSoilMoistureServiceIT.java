package de.app.fivegla.integration.agranimo;

import de.app.fivegla.SpringBootIntegrationTestBase;
import de.app.fivegla.integration.agranimo.model.Zone;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;

@SpringBootTest
class AgranimoSoilMoistureServiceIT extends SpringBootIntegrationTestBase {

    @Autowired
    private AgranimoSoilMoistureIntegrationService soilMoistureService;
    @Autowired
    private AgranimoZoneService agranimoZoneService;

    private final Instant since = Instant.ofEpochSecond(1662087600);
    private final Instant until = Instant.ofEpochSecond(1662617200);

    @Test
    void givenInvalidTimePeriodWhenFetchingWaterVolumeShouldNotCauseAnError() {
        List<Zone> zones = agranimoZoneService.fetchZones();
        zones.forEach(zone -> soilMoistureService.fetchWaterVolume(zone, until));
    }

    @Test
    void givenValidCredentialsWhenFetchingWaterHeightThenThereShouldBeEntriesForTheZone() {
        List<Zone> zones = agranimoZoneService.fetchZones();
        zones.forEach(zone -> soilMoistureService.fetchWaterHeight(zone, until));
    }

    @Test
    void givenValidCredentialsWhenFetchingWaterContentThenThereShouldBeEntriesForTheZone() {
        List<Zone> zones = agranimoZoneService.fetchZones();
        zones.forEach(zone -> soilMoistureService.fetchWaterContent(zone, until));
    }
}