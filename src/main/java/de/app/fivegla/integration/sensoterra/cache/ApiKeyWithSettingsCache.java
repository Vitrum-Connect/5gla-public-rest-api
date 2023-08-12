package de.app.fivegla.integration.sensoterra.cache;

import de.app.fivegla.integration.sensoterra.dto.ApiKeyWithSettings;
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
public class ApiKeyWithSettingsCache {

    public static final int TTL = 60;
    private ApiKeyWithSettings apiKeyWithSettings;
    private Instant validUntil;

    /**
     * Sets the credentials and updates the timestamp for the cache.
     *
     * @param apiKeyWithSettings the credentials to set.
     */
    public void setApiKeyWithSettings(ApiKeyWithSettings apiKeyWithSettings) {
        this.apiKeyWithSettings = apiKeyWithSettings;
        this.validUntil = Instant.now().plus(TTL, ChronoUnit.MINUTES);
    }

    /**
     * Checks if the cache is expired.
     *
     * @return true if the cache is expired.
     */
    public boolean isExpired() {
        return apiKeyWithSettings == null || Instant.now().isAfter(validUntil);
    }

    public String getApiKey() {
        return apiKeyWithSettings.getApiKey();
    }
}
