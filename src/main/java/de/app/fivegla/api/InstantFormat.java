package de.app.fivegla.api;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Instant formatter.
 */
public final class InstantFormat {

    private InstantFormat() {
    }

    /**
     * Format the given instant.
     *
     * @param instant The instant to format.
     * @return The formatted instant.
     */
    public static String format(Instant instant) {
        if (instant == null) {
            return null;
        } else {
            var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .withZone(ZoneId.systemDefault());
            return formatter.format(instant);
        }
    }
}
