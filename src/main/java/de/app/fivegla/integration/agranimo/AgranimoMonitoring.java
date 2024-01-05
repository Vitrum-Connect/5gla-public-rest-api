package de.app.fivegla.integration.agranimo;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.config.ApplicationConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * This class represents a third-party monitoring service that checks the health status of the system.
 * It uses a LoginIntegrationService to fetch the access token from an API and performs a health check based on the availability of the token.
 */
@Slf4j
@Component
@Endpoint(id = "agranimo")
@RequiredArgsConstructor
public class AgranimoMonitoring {

    private final ApplicationConfiguration applicationConfiguration;
    private final ZoneService zoneService;

    @ReadOperation
    public Health read() {
        if (!applicationConfiguration.isEnabled(Manufacturer.AGRANIMO)) {
            log.debug("Agranimo is disabled. Skipping health check.");
            return null;
        } else {
            try {
                var zones = zoneService.fetchZones();
                if (zones != null && !zones.isEmpty()) {
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
                    .message("Agranimo API is currently not available or did not return any access token.")
                    .build());
        }
    }
}
