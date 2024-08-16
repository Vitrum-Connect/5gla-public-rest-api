package de.app.fivegla.controller.dto.request;

import de.app.fivegla.controller.dto.request.inner.CameraImage;
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
public class StationaryImageProcessingRequest {

    @NotBlank
    @Schema(description = "The id of the camera.")
    private String cameraId;

    @Schema(description = "The images to process.")
    private List<CameraImage> images;

    @Schema(description = "A custom group ID, which can be used to group devices / measurements. This is optional, if not set, the default group will be used.")
    protected String groupId;
}
