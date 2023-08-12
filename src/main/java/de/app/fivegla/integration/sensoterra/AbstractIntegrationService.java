package de.app.fivegla.integration.sensoterra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * Abstract class for the integration services.
 */
@Slf4j
abstract class AbstractIntegrationService {

    private final ApiKeyIntegrationService apiKeyIntegrationService;

    @Value("${app.sensors.sensoterra.url}")
    protected String url;

    AbstractIntegrationService(ApiKeyIntegrationService apiKeyIntegrationService) {
        this.apiKeyIntegrationService = apiKeyIntegrationService;
    }

    protected String getApiKey() {
        return apiKeyIntegrationService.fetchApiKey();
    }
}
