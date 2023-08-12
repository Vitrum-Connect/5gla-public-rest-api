package de.app.fivegla.integration.agvolution;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.agvolution.cache.AccessTokenCache;
import de.app.fivegla.integration.agvolution.dto.Credentials;
import de.app.fivegla.integration.agvolution.dto.request.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service for login against the API.
 */
@Slf4j
@Service
public class AccessTokenService {

    @Value("${app.sensors.agvolution.url}")
    private String url;

    @Value("${app.sensors.agvolution.username}")
    private String username;

    @Value("${app.sensors.agvolution.password}")
    private String password;

    private final AccessTokenCache accessTokenCache;

    /**
     * Service for integration with Agvolution.
     */
    public AccessTokenService(AccessTokenCache accessTokenCache) {
        this.accessTokenCache = accessTokenCache;
    }

    /**
     * Fetch the access token from the API.
     */
    public String fetchAccessToken() {
        if (accessTokenCache.isExpired()) {
            try {
                var response = new RestTemplate().postForEntity(url + "/auth/session", new LoginRequest(username, password), Credentials.class);
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
