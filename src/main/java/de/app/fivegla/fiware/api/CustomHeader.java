package de.app.fivegla.fiware.api;

/**
 * This class represents a custom header used in Fiware Service requests.
 */
public interface CustomHeader {

    /**
     * The FIWARE_SERVICE variable represents the HTTP header key for specifying the Fiware service.
     * <p>
     * The value of this header is used to identify the Fiware service that the request is associated with.
     * It is typically used in RESTful API calls to provide service-specific information.
     * </p>
     * <p>
     * The header key is defined as "Fiware-Service".
     * </p>
     * <p>
     * Example usage:
     * <pre>{@code
     *     String fiwareService = "my-service";
     *     headers.put(FIWARE_SERVICE, fiwareService);
     * }</pre>
     * </p>
     */
    String FIWARE_SERVICE = "Fiware-Service";

}
