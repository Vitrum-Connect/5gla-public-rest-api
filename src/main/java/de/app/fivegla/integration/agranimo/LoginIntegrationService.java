package de.app.fivegla.integration.agranimo;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.agranimo.cache.UserDataCache;
import de.app.fivegla.integration.agranimo.model.Credentials;
import de.app.fivegla.integration.agranimo.dto.request.LoginRequest;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class LoginIntegrationService {

    @Value("${app.sensors.agranimo.url}")
    private String url;

    @Value("${app.sensors.agranimo.username}")
    private String username;

    @Value("${app.sensors.agranimo.password}")
    private String password;

    private final UserDataCache userDataCache;
    private final RestTemplate restTemplate;

    /**
     * Fetch the access token from the API.
     */
    public String fetchAccessToken() {
        if (userDataCache.isExpired()) {
            return getAccessTokenFromApi();
        } else {
            return userDataCache.getAccessToken();
        }
    }

    /**
     * Retrieves the access token from the API by performing a login request with the provided credentials.
     *
     * @return The access token retrieved from the API.
     * @throws BusinessException If an error occurs while logging in against the API.
     */
    private String getAccessTokenFromApi() {
        try {
            var response = restTemplate.postForEntity(url + "/auth/login", new LoginRequest(username, password), Credentials.class);
            if (response.getStatusCode() != HttpStatus.CREATED) {
                log.error("Error while login against the API. Status code: {}", response.getStatusCode());
                throw new BusinessException(ErrorMessage.builder()
                        .error(Error.AGRANIMO_COULD_NOT_LOGIN_AGAINST_API)
                        .message("Could not login against the API.")
                        .build());
            } else {
                log.info("Successfully logged in against the API.");
                var credentials = response.getBody();
                if (null == credentials) {
                    throw new BusinessException(ErrorMessage.builder()
                            .error(Error.AGRANIMO_COULD_NOT_LOGIN_AGAINST_API)
                            .message("Could not login against the API. Response was empty.")
                            .build());
                } else {
                    log.info("Access token found after successful: {}", credentials.getAccessToken());
                    userDataCache.setCredentials(credentials);
                    return credentials.getAccessToken();
                }
            }
        } catch (Exception e) {
            log.error("Error while login against the API.", e);
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.AGRANIMO_COULD_NOT_LOGIN_AGAINST_API)
                    .message("Could not login against the API.")
                    .build());
        }
    }

}
