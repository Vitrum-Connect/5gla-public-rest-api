package de.app.fivegla.integration.sensoterra;

import de.app.fivegla.SpringBootIntegrationTestBase;
import de.app.fivegla.integration.sensoterra.model.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProbeIntegrationServiceIT extends SpringBootIntegrationTestBase {

    @Autowired
    private ProbeIntegrationService probeIntegrationService;

    @Test
    void givenValidCredentialsWhenFetchingTheLocationsThenTheRequestShouldBeAccepted() {
        var location = new Location();
        location.setId("17551");
        var probes = probeIntegrationService.fetchAll(location);
        assertThat(probes).isNotNull();
        assertThat(probes).isNotEmpty();
    }


}