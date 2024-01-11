package de.app.fivegla.integration.soilscout.cache;

import de.app.fivegla.integration.soilscout.dto.response.AccessAndRefreshTokenResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Cache for the access token.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SoilScoutAccessTokenCache {

    /**
     * The access token is valid for 10 minutes. This is not the maximum time the access token is valid, we just assume that the access token is valid for this time. The maximum time defined by the API is 15 minutes.
     */
    private static final long ACCESS_TOKEN_VALIDITY = 10;

    /**
     * The refresh token is valid for 6 days and 12 hours. This is not the maximum time the refresh token is valid, we just assume that the refresh token is valid for this time. The maximum time defined by the API is 7 days.
     */
    private static final long REFRESH_TOKEN_VALIDITY = (6 * 24) + 12;

    private Instant lastTokenRequest;

    @Getter
    private AccessAndRefreshTokenResponse accessAndRefreshTokenResponse;

    /**
     * Check if the access token is still valid.
     *
     * @return true if the access token is still valid
     */
    public boolean isAccessTokenValid() {
        return lastTokenRequest != null && lastTokenRequest.plus(ACCESS_TOKEN_VALIDITY, ChronoUnit.MINUTES).isAfter(Instant.now());
    }

    /**
     * Check if the access token is still valid.
     *
     * @return true if the access token is still valid
     */
    public boolean isRefreshTokenValid() {
        return lastTokenRequest != null && lastTokenRequest.plus(REFRESH_TOKEN_VALIDITY, ChronoUnit.HOURS).isAfter(Instant.now());
    }

    /**
     * Update the access token.
     *
     * @param accessAndRefreshTokenResponse the new access token
     */
    public void updateAccessToken(AccessAndRefreshTokenResponse accessAndRefreshTokenResponse) {
        this.accessAndRefreshTokenResponse = accessAndRefreshTokenResponse;
        this.lastTokenRequest = Instant.now();
    }

}
