package de.app.fivegla.integration.agvolution;

import de.app.fivegla.SpringBootIntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccessTokenIntegrationServiceIT extends SpringBootIntegrationTestBase {

    @Autowired
    private AccessTokenIntegrationService accessTokenIntegrationService;

    @Test
    void givenValidCredentialsWhenLoginThenTheRequestShouldBeAccepted() {
        var optionalAccessToken = accessTokenIntegrationService.fetchAccessToken();
        assertThat(optionalAccessToken).isNotBlank();
    }
}