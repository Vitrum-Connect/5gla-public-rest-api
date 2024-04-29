package de.app.fivegla.fiware.api;

/**
 * Business exception.
 */
public class FiwareIntegrationLayerException extends RuntimeException {
    public FiwareIntegrationLayerException(String message) {
        super(message);
    }

    public FiwareIntegrationLayerException(String message, Exception e) {
        super(message, e);
    }
}
