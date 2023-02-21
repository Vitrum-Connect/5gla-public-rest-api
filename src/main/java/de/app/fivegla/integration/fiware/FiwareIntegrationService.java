package de.app.fivegla.integration.fiware;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service for integration with FIWARE.
 */
@Service
public class FiwareIntegrationService {

    @Value("${fiware.context-broker-url}")
    private String contextBrokerUrl;

}
