package de.app.fivegla.controller.dto.request.inner;

import de.app.fivegla.integration.imageprocessing.model.ImageChannel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "A single image to process.")
public class DroneImage {

    @Schema(description = "The channel of the image.")
    private ImageChannel imageChannel;

    @NotBlank
    @Schema(description = "The base64 encoded tiff image.")
    private String base64Image;

}
