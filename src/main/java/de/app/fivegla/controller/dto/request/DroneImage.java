package de.app.fivegla.controller.dto.request;

import de.app.fivegla.integration.imageprocessing.model.ImageChannel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * A single image to process.
 */
@Getter
@Setter
@Schema(description = "A single image to process.")
public class DroneImage extends BaseRequest {

    /**
     * The channel of the image since the value cannot be read from the EXIF.
     */
    @Schema(description = "The channel of the image.")
    private ImageChannel imageChannel;

    /**
     * The base64 encoded tiff image.
     */
    @NotBlank
    @Schema(description = "The base64 encoded tiff image.")
    private String base64Image;

}
