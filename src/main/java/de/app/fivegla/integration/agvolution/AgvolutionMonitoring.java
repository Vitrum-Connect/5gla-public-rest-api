package de.app.fivegla.integration.agvolution;

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
 * The AgvolutionMonitoring class is responsible for performing health checks on the Agvolution API.
 * It checks if the Agvolution sensor is enabled and retrieves an access token from the API.
 * If the sensor is disabled or the API call fails, it throws a BusinessException with the corresponding error message.
 */
@Slf4j
@Component
@Endpoint(id = "agvolution")
@RequiredArgsConstructor
public class AgvolutionMonitoring {

    private final ApplicationConfiguration applicationConfiguration;
    private final AccessTokenIntegrationService accessTokenIntegrationService;

    @ReadOperation
    public Health read() {
        if (!applicationConfiguration.isEnabled(Manufacturer.AGVOLUTION)) {
            log.debug("Agvolution is disabled. Skipping health check.");
            return null;
        } else {
            try {
                var accessToken = accessTokenIntegrationService.fetchAccessToken();
                if (StringUtils.isNotBlank(accessToken)) {
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
                    .message("Agvolution API is currently not available or did not return any access token.")
                    .build());
        }
    }
}
