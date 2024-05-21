package de.app.fivegla.controller.dto.response;

import de.app.fivegla.api.Response;
import de.app.fivegla.controller.dto.response.inner.ThirdPartyApiConfiguration;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "Response wrapper.")
public class CreateThirdPartyApiConfigurationResponse extends Response {

    /**
     * The third-party API configuration.
     */
    @Schema(description = "The third-party API configuration.")
    private ThirdPartyApiConfiguration thirdPartyApiConfiguration;

}
