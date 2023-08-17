package de.app.fivegla.integration.sentek;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * Abstract class for the integration services.
 */
@Slf4j
abstract class AbstractIntegrationService {

    @Value("${app.sensors.sentek.url}")
    protected String url;

    @Value("${app.sensors.sentek.apiToken}")
    protected String apiToken;

    protected String getAccessToken() {
        return apiToken;
    }

}
