package de.app.fivegla.monitoring;

/**
 * Custom metrics.
 */
public interface Metrics {

    /**
     * Prefix for the metric keys related to entities fetched.
     * <p>
     * The ENTITIES_FETCHED_PREFIX is used to construct metric keys for tracking the number of entities fetched.
     * The prefix is followed by a specific identifier that indicates the type or context of the entity.
     * This allows for better categorization and analysis of the fetched entities.
     * <p>
     * The ENTITIES_FETCHED_PREFIX is defined within the Metrics interface, which is a part of the custom metrics module.
     * This prefix is used for metrics tracking purposes and should not be modified or reassigned.
     *
     * @see Metrics
     */
    String ENTITIES_FETCHED_PREFIX = "app_5gla_entities_fetched_";

    /**
     * Represents the prefix for the metric keys related to errors during job execution.
     * <p>
     * The ERRORS_DURING_JOB_EXECUTION_PREFIX is used to construct metric keys for capturing
     * errors that occurred during the execution of a job. The prefix is followed by a specific
     * identifier that indicates the type or context of the error. This allows for better
     * categorization and analysis of the errors.
     * <p>
     * The ERRORS_DURING_JOB_EXECUTION_PREFIX is defined within the {@link Metrics} interface,
     * which is a part of the custom metrics module. This prefix is used for metrics tracking
     * purposes and should not be modified or reassigned.
     * </p>
     *
     * @see Metrics
     */
    String ERRORS_DURING_JOB_EXECUTION_PREFIX = "app_5gla_errors_during_job_execution_";

    /**
     * Represents the prefix for the metric keys related to job execution time.
     * <p>
     * The JOB_EXECUTION_TIME_PREFIX is used to construct metric keys for tracking the execution time
     * of a job. The prefix is followed by a specific identifier that indicates the type or context of the job.
     * This allows for better categorization and analysis of the job execution time.
     * <p>
     * The JOB_EXECUTION_TIME_PREFIX is defined within the {@link Metrics} interface,
     * which is a part of the custom metrics module. This prefix is used for metrics tracking
     * purposes and should not be modified or reassigned.
     * </p>
     *
     * @see Metrics
     */
    String JOB_EXECUTION_TIME_PREFIX = "app_5gla_job_execution_time_";
}
