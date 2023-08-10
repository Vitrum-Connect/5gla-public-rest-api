package de.app.fivegla.integration.agvolution.response;

import de.app.fivegla.integration.agvolution.response.inner.Devices;
import lombok.Getter;
import lombok.Setter;

/**
 * Response object.
 */
@Getter
@Setter
public class DeviceDataResponse {
    private Devices data;
}
