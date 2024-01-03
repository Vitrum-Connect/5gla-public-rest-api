package de.app.fivegla.persistence.entity;

import de.app.fivegla.api.Manufacturer;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Represents a job disabled for a specific manufacturer.
 */
@Getter
@Setter
public class DisabledJob {

    /**
     * Represents the timestamp when a job is disabled for a specific manufacturer.
     * <p>
     * The disabledAt variable is of type Instant. It represents the exact moment when the job is disabled for the manufacturer.
     * Instant is a class in Java that represents a moment on the timeline, measured in milliseconds from the Unix epoch of 1970-01-01T00:00:00Z.
     * <p>
     * The value of disabledAt can be set and retrieved using getter and setter methods. The getter method is named "getDisabledAt" and returns an Instant object.
     * The setter method is named "setDisabledAt" and accepts an Instant object as a parameter.
     * <p>
     * Example usage:
     * DisabledJob job = new DisabledJob();
     * Instant disabledAt = Instant.now(); // Get current timestamp
     * job.setDisabledAt(disabledAt); // Set disabledAt to the current timestamp
     * Instant retrievedDisabledAt = job.getDisabledAt(); // Get the disabledAt value
     * <p>
     * Note: This documentation assumes that you have the necessary imports and declarations for the Instant and DisabledJob classes.
     *
     * @see DisabledJob
     * @see Instant
     */
    private Instant disabledAt;

    /**
     * Represents a disabled manufacturer.
     */
    private Manufacturer disabledManufacturers;

}
