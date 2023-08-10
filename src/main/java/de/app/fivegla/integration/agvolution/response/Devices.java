package de.app.fivegla.integration.agvolution.response;

import de.app.fivegla.integration.agvolution.model.Device;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Response object for the devices.
 */
@Getter
@Setter
public class Devices {
    private List<Device> devices;
}
