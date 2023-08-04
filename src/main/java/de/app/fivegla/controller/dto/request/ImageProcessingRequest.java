package de.app.fivegla.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Request for image processing.
 */
@Getter
@Setter
@Schema(description = "Request for image processing.")
public class ImageProcessingRequest {

    /**
     * The id of the drone.
     */
    @NotBlank
    @Schema(description = "The id of the drone.")
    private String droneId;

    /**
     * The images to process.
     */
    @Schema(description = "The images to process.")
    private List<DroneImage> images;
}
