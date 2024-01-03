package de.app.fivegla.controller.dto.request;

import de.app.fivegla.api.Manufacturer;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a request to disable a job.
 */
@Getter
@Setter
public class DisableJobRequest {

    /**
     * Represents the manufacturer of a sensor or device.
     */
    private Manufacturer manufacturer;

}
