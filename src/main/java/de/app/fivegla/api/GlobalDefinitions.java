package de.app.fivegla.api;

/**
 * This interface contains global definitions for the application.
 */
public interface GlobalDefinitions {
    /**
     * The INSTANT_JSON_PATTERN is a string constant that represents the pattern used to format
     * and parse Instant objects in JSON format.
     * <p>
     * The pattern is defined as "yyyy-MM-dd'T'HH:mm:ss.SSS", where:
     * - "yyyy" represents the year using four digits
     * - "MM" represents the month using two digits
     * - "dd" represents the day using two digits
     * - "T" is a literal 'T' character, used to separate the date and time
     * - "HH" represents the hour using two digits in the 24-hour format
     * - "mm" represents the minute using two digits
     * - "ss" represents the second using two digits
     * - "SSS" represents the milliseconds using three digits
     * <p>
     * This pattern is commonly used in JSON APIs for representing Instant objects in a consistent manner.
     * It can be used to format Instant objects into strings or parse strings into Instant objects.
     * <p>
     * Example usage:
     * <pre>{@code
     *    String instantString = "2022-12-31T23:59:59.999";
     *
     *    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(INSTANT_JSON_PATTERN);
     *    Instant instant = Instant.parse(instantString, formatter);
     *
     *    String formattedInstant = formatter.format(instant);
     *    System.out.println(formattedInstant);
     * }</pre>
     */
    String INSTANT_JSON_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";
}
