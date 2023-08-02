package de.app.fivegla.integration.farm21;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * Abstract class for the integration services.
 */
@Slf4j
abstract class AbstractIntegrationService {

    @Value("${app.sensors.farm21.url}")
    protected String url;

    @Value("${app.sensors.farm21.api-token}")
    protected String apiToken;

    protected String getAccessToken() {
       return apiToken;
    }

}
