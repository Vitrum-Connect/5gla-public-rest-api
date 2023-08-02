package de.app.fivegla.controller.soilscout.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Base64;

/**
 * Request wrapper.
 */
@Getter
@Setter
@Schema(description = "CSV data import request.")
public class CsvSensorDataImportRequest {

    /**
     * The base64 encoded CSV data.
     */
    @Schema(description = "The base64 encoded CSV data.")
    private String base64EncodedCsvData;

    /**
     * Get the decoded CSV data.
     *
     * @return The decoded CSV data.
     */
    public String getDecodedCsvData() {
        return new String(Base64.getDecoder().decode(base64EncodedCsvData));
    }

}
