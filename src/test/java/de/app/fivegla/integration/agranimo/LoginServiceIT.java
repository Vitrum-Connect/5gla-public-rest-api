package de.app.fivegla.integration.agranimo;

import de.app.fivegla.SpringBootIntegrationTestBase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoginServiceIT extends SpringBootIntegrationTestBase {

    @Autowired
    private LoginService loginService;

    @Test
    void givenValidCredentialsWhenLoginThenTheRequestShouldBeAccepted() {
        var optionalAccessToken = loginService.fetchAccessToken();
        Assertions.assertThat(optionalAccessToken).isNotBlank();
    }
}