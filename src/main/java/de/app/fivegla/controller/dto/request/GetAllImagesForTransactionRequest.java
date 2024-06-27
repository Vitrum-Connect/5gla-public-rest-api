package de.app.fivegla.controller.dto.request;

import de.app.fivegla.persistence.entity.enums.ImageChannel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Request to get all images for a transaction.
 */
@Getter
@Setter
@Schema(description = "Request to get all images for a transaction.")
public class GetAllImagesForTransactionRequest {

    /**
     * The transaction id.
     */
    @NotBlank
    @Schema(description = "The transaction id.")
    private String transactionId;

    /**
     * The channel of the image.
     */
    @NotNull
    @Schema(description = "The channel of the image.")
    private ImageChannel channel;

}
