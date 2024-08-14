package de.app.fivegla.controller.dto.response;

import de.app.fivegla.api.Response;
import de.app.fivegla.controller.dto.response.inner.RegisteredDevice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "Response to register a device.")
public class RegisterDeviceResponse extends Response {

    @Schema(description = "The registered device.")
    private RegisteredDevice registeredDevice;
}
