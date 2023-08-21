package de.app.fivegla.api;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Instant formatter.
 */
public final class Format {

    private Format() {
    }

    /**
     * Formats a given Date object.
     *
     * @param date the Date object to be formatted
     * @return the formatted string representation of the date
     */
    public static String format(Date date) {
        return format(date.toInstant());
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

    /**
     * Format the current instant using a specific format for Irrimax.
     *
     * @return The formatted instant.
     */
    public static String formatForIrrimax(Instant instant) {
        var formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                .withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }
}
