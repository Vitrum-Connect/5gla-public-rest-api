package de.app.fivegla.fiware.api;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

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
     * Parses a string representation of a date and time into a TemporalAccessor object using the default date format.
     *
     * @param s The string representation of the date and time to be parsed.
     * @return A TemporalAccessor object representing the parsed date and time.
     */
    public static TemporalAccessor parse(String s) {
        return FMT.parse(s);
    }
}
