package de.app.fivegla;

import io.prometheus.client.CollectorRegistry;
import org.springframework.boot.test.mock.mockito.MockBean;

public class SpringBootIntegrationTestBase {

    @MockBean
    @SuppressWarnings("unused")
    private CollectorRegistry collectorRegistry;

}
