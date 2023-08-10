package de.app.fivegla.integration.agvolution;

import de.app.fivegla.SpringBootIntegrationTestBase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccessTokenServiceIT extends SpringBootIntegrationTestBase {

    @Autowired
    private AccessTokenService accessTokenService;

    @Test
    void givenValidCredentialsWhenLoginThenTheRequestShouldBeAccepted() {
        var optionalAccessToken = accessTokenService.fetchAccessToken();
        Assertions.assertThat(optionalAccessToken).isNotBlank();
    }
}