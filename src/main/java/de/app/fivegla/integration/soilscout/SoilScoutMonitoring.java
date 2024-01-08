package de.app.fivegla.integration.soilscout;

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
 * SoilScoutMonitoring class is responsible for monitoring the health and status
 * of SoilScout sensors.
 */
@Slf4j
@Component
@Endpoint(id = "soilscout")
@RequiredArgsConstructor
public class SoilScoutMonitoring {

    private final ApplicationConfiguration applicationConfiguration;
    private final SoilScoutSensorIntegrationService soilScoutSensorIntegrationService;

    @ReadOperation
    public Health read() {
        if (!applicationConfiguration.isEnabled(Manufacturer.SOILSCOUT)) {
            log.debug("SoilScout is disabled. Skipping health check.");
            return null;
        } else {
            try {
                var sensors = soilScoutSensorIntegrationService.fetchAll();
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
                    .message("SoilScout API is currently not available or did not return any sensors.")
                    .build());
        }
    }
}
