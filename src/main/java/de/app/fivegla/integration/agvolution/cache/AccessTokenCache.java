package de.app.fivegla.integration.agvolution.cache;

import de.app.fivegla.integration.agvolution.dto.Credentials;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Cache for internal data.
 */
@Slf4j
@Getter
@Component
public class AccessTokenCache {

    private Credentials credentials;
    private Instant validUntil;

    /**
     * Sets the credentials and updates the timestamp for the cache.
     *
     * @param credentials the credentials to set.
     */
    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
        this.validUntil = Instant.now().plus(credentials.getExpiresIn(), ChronoUnit.SECONDS);
    }

    /**
     * Checks if the cache is expired.
     *
     * @return true if the cache is expired.
     */
    public boolean isExpired() {
        return credentials == null || Instant.now().isAfter(validUntil);
    }

    public String getAccessToken() {
        return credentials.getAccessToken();
    }
}
