package de.app.fivegla.integration.agranimo;

import de.app.fivegla.SpringBootIntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccessTokenIntegrationServiceIT extends SpringBootIntegrationTestBase {

    @Autowired
    private LoginIntegrationService loginService;

    @Test
    void givenValidCredentialsWhenLoginThenTheRequestShouldBeAccepted() {
        var optionalAccessToken = loginService.fetchAccessToken();
        assertThat(optionalAccessToken).isNotBlank();
    }
}