package de.app.fivegla.integration.sensoterra;

import de.app.fivegla.SpringBootIntegrationTestBase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiKeyIntegrationServiceIT extends SpringBootIntegrationTestBase {

    @Autowired
    private ApiKeyIntegrationService apiKeyIntegrationService;

    @Test
    void givenValidCredentialsWhenLoginThenTheRequestShouldBeAccepted() {
        var optionalAccessToken = apiKeyIntegrationService.fetchApiKey();
        Assertions.assertThat(optionalAccessToken).isNotBlank();
    }
}