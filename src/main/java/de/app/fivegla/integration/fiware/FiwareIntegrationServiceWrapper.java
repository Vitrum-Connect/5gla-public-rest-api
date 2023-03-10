package de.app.fivegla.integration.fiware;


import de.app.fivegla.fiware.FiwareIntegrationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * Service for integration with FIWARE.
 */
@Service
public class FiwareIntegrationServiceWrapper {

    @Value("${fiware.context-broker-url}")
    private String contextBrokerUrl;

    @Bean
    public FiwareIntegrationService fiwareIntegrationService() {
        return new FiwareIntegrationService(contextBrokerUrl);
    }

}
