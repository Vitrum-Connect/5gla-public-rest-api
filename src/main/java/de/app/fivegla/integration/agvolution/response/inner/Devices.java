package de.app.fivegla.integration.agvolution.response.inner;

import de.app.fivegla.integration.agvolution.model.Device;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Response object.
 */
@Getter
@Setter
public class Devices {
    private List<Device> devices;
}
