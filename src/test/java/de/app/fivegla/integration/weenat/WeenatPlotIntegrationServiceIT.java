package de.app.fivegla.integration.weenat;

import de.app.fivegla.SpringBootIntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WeenatPlotIntegrationServiceIT extends SpringBootIntegrationTestBase {

    @Autowired
    private WeenatPlotIntegrationService weenatPlotIntegrationService;

    @Test
    void givenValidCredentialsWhenLoginThenTheRequestShouldBeAccepted() {
        var metadata = weenatPlotIntegrationService.fetchAll();
        assertThat(metadata).isNotNull();
        assertThat(metadata).isNotEmpty();
    }
}