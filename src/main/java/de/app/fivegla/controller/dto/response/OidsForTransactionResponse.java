package de.app.fivegla.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Response wrapper.
 */
@Getter
@Builder
@Schema(description = "Response wrapper.")
public class OidsForTransactionResponse {

    /**
     * The transaction id.
     */
    @Schema(description = "The transaction id.")
    private String transactionId;

    /**
     * The OIDs for the images.
     */
    @Schema(description = "The OIDs for the images.")
    private List<String> oids;

}
