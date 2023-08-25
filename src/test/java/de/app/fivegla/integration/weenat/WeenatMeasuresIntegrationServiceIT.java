package de.app.fivegla.integration.weenat;

import de.app.fivegla.SpringBootIntegrationTestBase;
import de.app.fivegla.integration.weenat.model.Measurements;
import de.app.fivegla.integration.weenat.model.Plot;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@SpringBootTest
class WeenatMeasuresIntegrationServiceIT extends SpringBootIntegrationTestBase {

    @Autowired
    private WeenatMeasuresIntegrationService weenatMeasuresIntegrationService;

    @Test
    void givenValidCredentialsWhenLoginThenTheRequestShouldBeAccepted() {
        var fetchedWeenatMeasures = weenatMeasuresIntegrationService.fetchAll(Instant.now().minus(1, ChronoUnit.DAYS));
        Assertions.assertThat(fetchedWeenatMeasures).isNotNull();
        Assertions.assertThat(fetchedWeenatMeasures).isNotEmpty();
    }
}