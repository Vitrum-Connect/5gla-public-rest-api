package de.app.fivegla.integration.sentek;

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

@Slf4j
@Component
@Endpoint(id = "sentek")
@RequiredArgsConstructor
public class SentekMonitoring {

    private final ApplicationConfiguration applicationConfiguration;
    private final SentekSensorIntegrationService sentekSensorIntegrationService;

    @ReadOperation
    public Health read() {
        if (!applicationConfiguration.isEnabled(Manufacturer.SENTEK)) {
            log.debug("Sentek is disabled. Skipping health check.");
            return null;
        } else {
            try {
                var sensors = sentekSensorIntegrationService.fetchAll();
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
                    .message("Sentek API is currently not available or did not return any sensors.")
                    .build());
        }
    }
}
