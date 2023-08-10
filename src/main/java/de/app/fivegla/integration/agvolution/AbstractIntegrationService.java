package de.app.fivegla.integration.agvolution;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * Abstract class for the integration services.
 */
@Slf4j
abstract class AbstractIntegrationService {

    private final AccessTokenService accessTokenService;

    @Value("${app.sensors.agvolution.url}")
    protected String url;

    AbstractIntegrationService(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

    protected String getAccessToken() {
        return accessTokenService.fetchAccessToken();
    }
}
