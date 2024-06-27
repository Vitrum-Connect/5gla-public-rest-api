package de.app.fivegla.controller.dto.response;

import de.app.fivegla.api.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Response to get all images for a transaction.
 */
@Getter
@Setter
@Builder
@Schema(description = "Response to get all images for a transaction.")
public class GetAllImagesForTransactionResponse extends Response {

    /**
     * The images as base64 encoded data.
     */
    @Schema(description = "The images as base64 encoded data.")
    private List<String> imagesAsBase64EncodedData;
}
