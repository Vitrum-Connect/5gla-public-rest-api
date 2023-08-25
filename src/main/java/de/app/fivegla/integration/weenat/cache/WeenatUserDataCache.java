package de.app.fivegla.integration.weenat.cache;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Cache for internal data.
 */
@Slf4j
@Getter
@Setter
@Component
public class WeenatUserDataCache {

    public static final int TTL = 60;
    private Instant validUntil;
    private String accessToken;

    /**
     * Checks if the cache is expired.
     *
     * @return true if the cache is expired.
     */
    public boolean isExpired() {
        return validUntil == null || Instant.now().isAfter(validUntil);
    }

    public void setAccessToken(String accessToken) {
        this.validUntil = Instant.now().plusSeconds(TTL);
        this.accessToken = accessToken;
    }
}
