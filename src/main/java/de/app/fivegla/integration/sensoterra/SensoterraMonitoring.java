package de.app.fivegla.integration.sensoterra;

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
 * The SensoterraMonitoring class is responsible for performing health checks on the Sensoterra API.
 */
@Slf4j
@Component
@Endpoint(id = "sensoterra")
@RequiredArgsConstructor
public class SensoterraMonitoring {

    private final ApplicationConfiguration applicationConfiguration;
    private final LocationIntegrationService locationIntegrationService;

    @ReadOperation
    public Health read() {
        if (!applicationConfiguration.isEnabled(Manufacturer.SENSOTERRA)) {
            log.debug("Sensoterra is disabled. Skipping health check.");
            return null;
        } else {
            try {
                var locations = locationIntegrationService.fetchAll();
                if (locations != null && !locations.isEmpty()) {
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
                    .message("Sensoterra API is currently not available or did not return any sensors.")
                    .build());
        }
    }
}
