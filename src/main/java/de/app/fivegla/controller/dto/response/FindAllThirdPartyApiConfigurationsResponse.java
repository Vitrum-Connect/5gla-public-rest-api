package de.app.fivegla.controller.dto.response;

import de.app.fivegla.api.Response;
import de.app.fivegla.controller.dto.response.inner.ThirdPartyApiConfiguration;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents the response for finding all third-party API configurations.
 */
@Getter
@Setter
@Builder
@Schema(name = "Response for finding all third API responses.")
public class FindAllThirdPartyApiConfigurationsResponse extends Response {

    /**
     * The list of third-party API configurations.
     */
    @Schema(description = "The list of third-party API configurations.")
    private List<ThirdPartyApiConfiguration> thirdPartyApiConfigurations;

}
