package de.app.fivegla.controller.dto.response.inner;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Image.
 */
@Getter
@Setter
@Builder
@Schema(description = "A registered device.")
public class RegisteredDevice {

    /**
     * The oid.
     */
    @Schema(description = "The oid.")
    private String oid;

    /**
     * The name.
     */
    @Schema(description = "The name.")
    private String name;

    /**
     * The description.
     */
    @Schema(description = "The description.")
    private String description;

    /**
     * The location of the image.
     */
    @Schema(description = "The longitude.")
    private double longitude;

    /**
     * The location.
     */
    @Schema(description = "The latitude.")
    private double latitude;

    /**
     * The group.
     */
    @Schema(description = "The ID of the group.")
    private String groupId;

}
