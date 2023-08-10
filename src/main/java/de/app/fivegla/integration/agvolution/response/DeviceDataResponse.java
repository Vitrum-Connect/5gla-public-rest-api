package de.app.fivegla.integration.agvolution.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Response object for the devices.
 */
@Getter
@Setter
public class DeviceDataResponse {
    @JsonProperty("data")
    private Devices data;
}
