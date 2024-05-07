package de.app.fivegla.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request for importing agri-crop data from a CSV file.")
public class ImportAgriCropFromCsvRequest extends BaseRequest {

    /**
     * The ID of the crop.
     */
    @Schema(description = "The ID of the crop.")
    private String cropId;

    /**
     * The CSV data.
     */
    @Schema(description = "The CSV data.")
    private String csvData;

}
