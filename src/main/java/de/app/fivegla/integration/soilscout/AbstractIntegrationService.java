package de.app.fivegla.integration.soilscout;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.soilscout.cache.AccessTokenCache;
import de.app.fivegla.integration.soilscout.dto.request.SsoRequest;
import de.app.fivegla.integration.soilscout.dto.request.TokenRefreshRequest;
import de.app.fivegla.integration.soilscout.dto.request.TokenRequest;
import de.app.fivegla.integration.soilscout.dto.response.AccessAndRefreshTokenResponse;
import de.app.fivegla.integration.soilscout.dto.response.SsoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Objects;

/**
 * Abstract soil scout integration service.
 */
@Slf4j
abstract class AbstractIntegrationService {

    @Value("${app.sensors.soilscout.url}")
    protected String url;

    @Value("${app.sensors.soilscout.username}")
    private String username;

    @Value("${app.sensors.soilscout.password}")
    private String password;

    @Autowired
    protected AccessTokenCache accessTokenCache;

   protected String getAccessToken() {
        if (accessTokenCache.isAccessTokenValid()) {
            log.debug("Access token is still valid. Using the access token from cache.");
        } else {
            getBearerToken();
        }
        return accessTokenCache.getAccessAndRefreshTokenResponse().getAccess();
    }

    private void getBearerToken() {
        if (accessTokenCache.isRefreshTokenValid()) {
            log.debug("Refresh token is still valid. Fetching a new access / refresh token.");
            var soilScoutTokenRefreshRequest = TokenRefreshRequest.builder()
                    .refreshToken(accessTokenCache.getAccessAndRefreshTokenResponse().getRefresh())
                    .build();
            var restTemplate = new RestTemplate();
            var headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            var httpEntity = new HttpEntity<>(soilScoutTokenRefreshRequest, headers);
            var response = restTemplate.exchange(url + "/auth/token/refresh/", HttpMethod.POST, httpEntity, AccessAndRefreshTokenResponse.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                accessTokenCache.updateAccessToken(response.getBody());
            } else {
                var errorMessage = ErrorMessage.builder().error(Error.SOIL_SCOUT_COULD_NOT_AUTHENTICATE).message("Could not fetch bearer token for SoilScout API.").build();
                throw new BusinessException(errorMessage);
            }
        } else {
            log.debug("Refresh token is not valid. Fetching a new access / refresh token.");
            String ssoToken = getSsoToken();
            var soilScoutTokenRequest = TokenRequest.builder()
                    .ssoToken(ssoToken)
                    .build();
            var restTemplate = new RestTemplate();
            var headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            var httpEntity = new HttpEntity<>(soilScoutTokenRequest, headers);
            var response = restTemplate.exchange(url + "/auth/token/sso/", HttpMethod.POST, httpEntity, AccessAndRefreshTokenResponse.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                accessTokenCache.updateAccessToken(response.getBody());
            } else {
                var errorMessage = ErrorMessage.builder().error(Error.SOIL_SCOUT_COULD_NOT_AUTHENTICATE).message("Could not fetch bearer token for SoilScout API.").build();
                throw new BusinessException(errorMessage);
            }
        }
    }

    private String getSsoToken() {
        var soilScoutSsoRequest = SsoRequest.builder()
                .username(username)
                .password(password)
                .build();
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        var httpEntity = new HttpEntity<>(soilScoutSsoRequest, headers);
        var response = restTemplate.exchange(url + "/auth/login/sso/", HttpMethod.POST, httpEntity, SsoResponse.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return Objects.requireNonNull(response.getBody()).getSsoToken();
        } else {
            var errorMessage = ErrorMessage.builder().error(Error.SOIL_SCOUT_COULD_NOT_AUTHENTICATE).message("Could not fetch SSO token for SoilScout API.").build();
            throw new BusinessException(errorMessage);
        }
    }

}
