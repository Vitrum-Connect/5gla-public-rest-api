package de.app.fivegla.controller.dto.response;

import de.app.fivegla.api.Response;
import de.app.fivegla.api.dto.SortableImageOids;
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
public class OidsForTransactionResponse extends Response {

    /**
     * The transaction id.
     */
    @Schema(description = "The transaction id.")
    private String transactionId;

    /**
     * The OIDs for the images.
     */
    @Schema(description = "The OIDs for the images.")
    private List<SortableImageOids> oids;

}
