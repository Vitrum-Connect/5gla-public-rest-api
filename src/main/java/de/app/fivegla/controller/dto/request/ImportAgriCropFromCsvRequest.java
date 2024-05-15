package de.app.fivegla.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request for importing agri-crop data from a CSV file.")
public class ImportAgriCropFromCsvRequest {

    @Schema(description = "The ID of the crop.")
    private String cropId;

    @Schema(description = "The CSV data.")
    private String csvData;

    @Schema(description = "A custom group ID, which can be used to group devices / measurements. This is optional, if not set, the default group will be used.")
    protected String groupId;

}
