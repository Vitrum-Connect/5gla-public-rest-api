package de.app.fivegla.controller;

import de.app.fivegla.controller.dto.request.CsvSensorDataImportRequest;
import de.app.fivegla.integration.csv.CsvDataImportIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for sensor data import purpose.
 */
@RestController
@RequestMapping("/csv-sensor-data-import")
public class CsvSensorDataImportController {

    private final CsvDataImportIntegrationService csvDataImportIntegrationService;

    public CsvSensorDataImportController(CsvDataImportIntegrationService csvDataImportIntegrationService) {
        this.csvDataImportIntegrationService = csvDataImportIntegrationService;
    }

    /**
     * Import sensor data from CSV.
     *
     * @param request The request.
     * @return The response.
     */
    @Operation(
            operationId = "csv-sensor-data-import.import",
            description = "Import sensor data from CSV."
    )
    @ApiResponse(
            responseCode = "200",
            description = "The import has been started."
    )
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> importSensorDataFromCsv(@Valid @RequestBody CsvSensorDataImportRequest request) {
        csvDataImportIntegrationService.importCsvData(request.getDecodedCsvData());
        return ResponseEntity.ok().build();
    }

}
