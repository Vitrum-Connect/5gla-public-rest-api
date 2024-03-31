package de.app.fivegla.integration.agvolution;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.agvolution.cache.AccessTokenCache;
import de.app.fivegla.integration.agvolution.dto.Credentials;
import de.app.fivegla.integration.agvolution.dto.request.LoginRequest;
import de.app.fivegla.persistence.entity.ThirdPartyApiConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service for login against the API.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccessTokenIntegrationService {

    private final AccessTokenCache accessTokenCache;
    private final RestTemplate restTemplate;

    /**
     * Fetch the access token from the API.
     */
    public String fetchAccessToken(ThirdPartyApiConfiguration thirdPartyApiConfiguration) {
        var url = thirdPartyApiConfiguration.getUrl();
        var username = thirdPartyApiConfiguration.getUsername();
        var password = thirdPartyApiConfiguration.getPassword();
        if (accessTokenCache.isExpired()) {
            try {
                var response = restTemplate.postForEntity(url + "/auth/session", new LoginRequest(username, password), Credentials.class);
                if (response.getStatusCode() != HttpStatus.OK) {
                    log.error("Error while login against the API. Status code: {}", response.getStatusCode());
                    throw new BusinessException(ErrorMessage.builder()
                            .error(Error.AGVOLUTION_COULD_NOT_LOGIN_AGAINST_API)
                            .message("Could not login against the API.")
                            .build());
                } else {
                    log.info("Successfully logged in against the API.");
                    var credentials = response.getBody();
                    if (null == credentials) {
                        throw new BusinessException(ErrorMessage.builder()
                                .error(Error.AGVOLUTION_COULD_NOT_LOGIN_AGAINST_API)
                                .message("Could not login against the API. Response was empty.")
                                .build());
                    } else {
                        log.info("Access token found: {}", credentials.getAccessToken());
                        accessTokenCache.setCredentials(credentials);
                        return credentials.getAccessToken();
                    }
                }
            } catch (Exception e) {
                log.error("Error while login against the API.", e);
                throw new BusinessException(ErrorMessage.builder()
                        .error(Error.AGVOLUTION_COULD_NOT_LOGIN_AGAINST_API)
                        .message("Could not login against the API.")
                        .build());
            }
        } else {
            return accessTokenCache.getAccessToken();
        }
    }

}
