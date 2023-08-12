package de.app.fivegla.integration.sensoterra;

import de.app.fivegla.SpringBootIntegrationTestBase;
import de.app.fivegla.integration.sensoterra.model.Probe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProbeDataIntegrationServiceIT extends SpringBootIntegrationTestBase {

    @Autowired
    private ProbeDataIntegrationService probeDataIntegrationService;

    @Test
    void givenValidCredentialsWhenFetchingTheLocationsThenTheRequestShouldBeAccepted() {
        var probe = new Probe();
        probe.setId(49205);
        var probes = probeDataIntegrationService.fetchAll(probe, Instant.now().minus(1, ChronoUnit.DAYS));
        assertThat(probes).isNotNull();
        assertThat(probes).isNotEmpty();
    }


}