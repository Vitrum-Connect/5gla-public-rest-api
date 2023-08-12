package de.app.fivegla.integration.agvolution.dto.response;

import de.app.fivegla.integration.agvolution.dto.response.inner.DeviceTimeSeries;
import lombok.Getter;
import lombok.Setter;

/**
 * Response object.
 */
@Getter
@Setter
public class DeviceTimeseriesDataResponse {
    private DeviceTimeSeries data;
}
