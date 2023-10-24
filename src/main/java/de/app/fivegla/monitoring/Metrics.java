package de.app.fivegla.monitoring;

/**
 * Custom metrics.
 */
public interface Metrics {

    /**
     * Prefix for the manufacturer-specific entity counter.
     */
    String ENTITIES_FETCHED_PREFIX = "app_5gla_entities_fetched_";

    /**
     * Prefix for the manufacturer-specific job runs.
     */
    String NR_OF_JOB_RUNS = "app_5gla_number_of_job_runs_";

    /**
     * Number of entities saved to FIWARE.
     */
    String FIWARE_SAVED_ENTITIES = "app_5gla_fiware_entities_saved";

    /**
     * Prefix for the manufacturer-specific entity counter.
     */
    String FIWARE_SAVED_ENTITIES_PREFIX = FIWARE_SAVED_ENTITIES + "_";


    /**
     * Number of sensors saved to FIWARE.
     */
    String FIWARE_SAVED_SENSORS = "app_5gla_fiware_sensors_saved";

    /**
     * Prefix for the manufacturer-specific entity counter.
     */
    String FIWARE_SAVED_SENSORS_PREFIX = FIWARE_SAVED_SENSORS + "_";

    /**
     * This variable represents the name of the FIWARE registered devices.
     * The value is set to "app_5gla_fiware_devices_registered".
     */
    String FIWARE_REGISTERED_DEVICES = "app_5gla_fiware_devices_registered";

    /**
     * Prefix used for registering devices in the FIWARE platform.
     * The prefix is appended to the device ID to create a unique registration key.
     */
    String FIWARE_REGISTERED_DEVICES_PREFIX = FIWARE_REGISTERED_DEVICES + "_";
}
