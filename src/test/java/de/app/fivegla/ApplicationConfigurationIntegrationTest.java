package de.app.fivegla;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationConfigurationIntegrationTest extends SpringBootIntegrationTestBase {

    @Value("${app.fiware.subscriptions.notificationUrls}")
    private String[] notificationUrls;

    @Test
    void givenValidConfigurationWhenResolvingNotificationUrlsThenReturnValidUrls() {
        assertThat(notificationUrls).isNotEmpty();
        assertThat(notificationUrls.length).isEqualTo(2);
        assertThat(notificationUrls[0]).isEqualTo("https://cygnus1.5gla.de");
        assertThat(notificationUrls[1]).isEqualTo("https://cygnus2.5gla.de");
    }

}
