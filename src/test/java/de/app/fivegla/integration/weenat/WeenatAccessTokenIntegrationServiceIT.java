package de.app.fivegla.integration.weenat;

import de.app.fivegla.SpringBootIntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WeenatAccessTokenIntegrationServiceIT extends SpringBootIntegrationTestBase {

    @Autowired
    private WeenatAccessTokenIntegrationService weenatAccessTokenIntegrationService;

    @Test
    void givenValidCredentialsWhenLoginThenTheRequestShouldBeAccepted() {
        var optionalAccessToken = weenatAccessTokenIntegrationService.fetchAccessToken();
        assertThat(optionalAccessToken).isNotBlank();
    }
}