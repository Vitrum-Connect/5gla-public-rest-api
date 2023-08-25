package de.app.fivegla.integration.weenat;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.sensoterra.dto.request.LoginRequest;
import de.app.fivegla.integration.weenat.cache.WeenatUserDataCache;
import de.app.fivegla.integration.weenat.response.AccessTokenResponse;
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
public class WeenatAccessTokenIntegrationService {

    private final WeenatUserDataCache userDataCache;

    @Value("${app.sensors.weenat.url}")
    private String url;

    @Value("${app.sensors.weenat.username}")
    private String username;

    @Value("${app.sensors.weenat.password}")
    private String password;


    /**
     * Service for integration with Agranimo.
     */
    public WeenatAccessTokenIntegrationService(WeenatUserDataCache userDataCache) {
        this.userDataCache = userDataCache;
    }


    /**
     * Retrieves the access token from Agranimo API for authentication.
     * If the access token is expired, it will attempt to log in and fetch a new access token from the API.
     *
     * @return The retrieved access token.
     * @throws BusinessException If there is an error during the login process or if the login response is empty.
     */
    public String fetchAccessToken() {
        if (userDataCache.isExpired()) {
            try {
                var response = new RestTemplate().postForEntity(url + "/api-token-auth", new LoginRequest(username, password), AccessTokenResponse.class);
                if (response.getStatusCode() != HttpStatus.OK) {
                    log.error("Error while login against the API. Status code: {}", response.getStatusCode());
                    throw new BusinessException(ErrorMessage.builder()
                            .error(Error.WEENAT_COULD_NOT_LOGIN_AGAINST_API)
                            .message("Could not login against the API.")
                            .build());
                } else {
                    log.info("Successfully logged in against the API.");
                    var accessTokenResponse = response.getBody();
                    if (null == accessTokenResponse) {
                        throw new BusinessException(ErrorMessage.builder()
                                .error(Error.WEENAT_COULD_NOT_LOGIN_AGAINST_API)
                                .message("Could not login against the API. Response was empty.")
                                .build());
                    } else {
                        log.info("Access token found after successful: {}", accessTokenResponse.getToken());
                        userDataCache.setAccessToken(accessTokenResponse.getToken());
                        return accessTokenResponse.getToken();
                    }
                }
            } catch (Exception e) {
                log.error("Error while login against the API.", e);
                throw new BusinessException(ErrorMessage.builder()
                        .error(Error.AGRANIMO_COULD_NOT_LOGIN_AGAINST_API)
                        .message("Could not login against the API.")
                        .build());
            }
        } else {
            return userDataCache.getAccessToken();
        }
    }

}
