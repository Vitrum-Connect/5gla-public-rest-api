package de.app.fivegla.integration.fiware;

import lombok.extern.slf4j.Slf4j;

/**
 * Abstract integration service.
 */
@Slf4j
public abstract class AbstractIntegrationService {
    private final String contextBrokerUrl;

    public AbstractIntegrationService(String contextBrokerUrl) {
        this.contextBrokerUrl = contextBrokerUrl;
    }

    /**
     * Returns the URL of the context broker.
     *
     * @return the URL of the context broker
     */
    String contextBrokerUrl() {
        return contextBrokerUrl;
    }

    /**
     * Returns the URL of the context broker for commands.
     *
     * @return the URL of the context broker for commands
     */
    String contextBrokerUrlForCommands() {
        return contextBrokerUrl + "/v2";
    }


}
