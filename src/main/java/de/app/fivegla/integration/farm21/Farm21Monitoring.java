package de.app.fivegla.integration.farm21;

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
 * The Farm21Monitoring class is responsible for monitoring the health of the Farm21 sensor integration service.
 */
@Slf4j
@Component
@Endpoint(id = "farm21")
@RequiredArgsConstructor
public class Farm21Monitoring {

    private final ApplicationConfiguration applicationConfiguration;
    private final Farm21SensorIntegrationService farm21SensorIntegrationService;

    @ReadOperation
    public Health read() {
        if (!applicationConfiguration.isEnabled(Manufacturer.FARM21)) {
            log.debug("Farm21 is disabled. Skipping health check.");
            return null;
        } else {
            try {
                var sensors = farm21SensorIntegrationService.fetchAll();
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
                    .message("Farm21 API is currently not available or did not return any sensors.")
                    .build());
        }
    }
}
