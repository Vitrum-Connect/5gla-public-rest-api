package de.app.fivegla.integration.agranimo.cache;

import de.app.fivegla.integration.agranimo.dto.Credentials;
import de.app.fivegla.integration.agranimo.dto.Zone;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Cache for internal data.
 */
@Slf4j
@Getter
@Component
public class UserDataCache {

    public static final int TTL = 60;
    private Credentials credentials;
    private List<Zone> zones;
    private Instant validUntil;

    /**
     * Sets the credentials and updates the timestamp for the cache.
     *
     * @param credentials the credentials to set.
     */
    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
        this.zones = null;
        this.validUntil = Instant.now().plus(TTL, ChronoUnit.MINUTES);
    }

    /**
     * Sets the zones and updates the timestamp for the cache.
     *
     * @param zones the zones to set.
     */
    public void setZones(List<Zone> zones) {
        this.zones = zones;
        this.validUntil = Instant.now().plus(TTL, ChronoUnit.MINUTES);
    }

    /**
     * Checks if the cache is expired.
     *
     * @return true if the cache is expired.
     */
    public boolean isExpired() {
        return credentials == null || zones == null || Instant.now().isAfter(validUntil);
    }

    public String getAccessToken() {
        return credentials.getAccessToken();
    }
}
