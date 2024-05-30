package de.app.fivegla.integration.fiware.api;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * A class that provides custom date formatting functionality.
 */
public final class CustomDateFormatter {

    private CustomDateFormatter() {
        // Prevent instantiation.
    }

    private static final DateTimeFormatter FMT = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .withZone(ZoneId.systemDefault());

    /**
     * Formats the given Instant object using a custom date format.
     *
     * @param instant The Instant object to be formatted.
     * @return The formatted date string.
     */
    public static String format(Instant instant) {
        return FMT.format(instant);
    }

    /**
     * Formats the given Date object using a custom date format.
     *
     * @param date The Date object to be formatted.
     * @return The formatted date string.
     */
    public static String format(Date date) {
        return FMT.format(date.toInstant());
    }

}
