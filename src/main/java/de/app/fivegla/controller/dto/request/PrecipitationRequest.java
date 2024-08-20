package de.app.fivegla.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Schema(description = "Represents a request to add a precipitation measurement.")
public class PrecipitationRequest {

    @Schema(description = "The group id.")
    private String groupId;

    @NotNull
    @Schema(description = "The date the precipitation was created.")
    private Date dateCreated;

    @Schema(description = "The latitude.")
    private double latitude;

    @Schema(description = "The longitude.")
    private double longitude;

    @Schema(description = "The temperature.")
    private double temp;

    @Schema(description = "The humidity.")
    private double humidity;

    @Schema(description = "The precipitation.")
    private double precipitation;

}
