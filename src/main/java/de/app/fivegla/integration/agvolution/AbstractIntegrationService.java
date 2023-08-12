package de.app.fivegla.integration.agvolution;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * Abstract class for the integration services.
 */
@Slf4j
abstract class AbstractIntegrationService {

    private final AccessTokenIntegrationService accessTokenIntegrationService;

    @Value("${app.sensors.agvolution.url}")
    protected String url;

    AbstractIntegrationService(AccessTokenIntegrationService accessTokenIntegrationService) {
        this.accessTokenIntegrationService = accessTokenIntegrationService;
    }

    protected String getAccessToken() {
        return accessTokenIntegrationService.fetchAccessToken();
    }
}
