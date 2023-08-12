package de.app.fivegla.integration.sensoterra;

import de.app.fivegla.SpringBootIntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LocationIntegrationServiceIT extends SpringBootIntegrationTestBase {

    @Autowired
    private LocationIntegrationService locationIntegrationService;

    @Test
    void givenValidCredentialsWhenFetchingTheLocationsThenTheRequestShouldBeAccepted() {
        var locations = locationIntegrationService.fetchAll();
        assertThat(locations).isNotNull();
        assertThat(locations).isNotEmpty();
    }


}