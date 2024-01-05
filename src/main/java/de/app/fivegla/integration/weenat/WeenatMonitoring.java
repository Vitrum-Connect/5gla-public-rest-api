package de.app.fivegla.integration.weenat;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.config.ApplicationConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * The WeenatMonitoring class represents a monitoring service for Weenat API access.
 */
@Slf4j
@Component
@Endpoint(id = "weenat")
@RequiredArgsConstructor
public class WeenatMonitoring {

    private final ApplicationConfiguration applicationConfiguration;
    private final WeenatPlotIntegrationService weenatPlotIntegrationService;

    @ReadOperation
    public Health read() {
        if (!applicationConfiguration.isEnabled(Manufacturer.WEENAT)) {
            log.debug("Weenat is disabled. Skipping health check.");
            return null;
        } else {
            try {
                var sensors = weenatPlotIntegrationService.fetchAll();
                if (sensors != null && !sensors.isEmpty()) {
                    return Health
                            .up()
                            .build();
                }
            } catch (Exception e) {
                log.error("Error while performing health check", e);
            }
            throw new BusinessException(ErrorMessage
                    .builder()
                    .error(Error.THIRD_PARTY_SERVICE_UNAVAILABLE)
                    .message("Weenat API is currently not available or did not return any sensors.")
                    .build());
        }
    }
}
