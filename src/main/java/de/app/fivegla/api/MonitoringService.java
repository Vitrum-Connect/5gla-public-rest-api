package de.app.fivegla.api;

import org.springframework.http.HttpStatus;

public interface MonitoringService {

    /**
     * Performs a health check and returns the status of the system.
     * The health check is typically used to check the availability and functioning
     * of important components and services in the system.
     *
     * @return the HttpStatus representing the health status of the system
     */
    HttpStatus healthCheck();

}
