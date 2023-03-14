package de.app.fivegla.controller;

import de.app.fivegla.integration.soilscout.job.SoilScoutScheduledDataImport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for sensor data import purpose, will only be active if the profile is set correctly.
 */
@RestController
@RequestMapping("/sensor-data-import")
@Profile("manual-import-allowed")
public class SensorDataImportController {

    private final SoilScoutScheduledDataImport soilScoutScheduledDataImport;

    public SensorDataImportController(SoilScoutScheduledDataImport soilScoutScheduledDataImport) {
        this.soilScoutScheduledDataImport = soilScoutScheduledDataImport;
    }

    /**
     * Run the import.
     */
    @Operation(
            operationId = "sensor-data-import.run",
            description = "Run the import manually."
    )
    @ApiResponse(
            responseCode = "200",
            description = "The import was started."
    )
    @PostMapping(value = "/run", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> runImport() {
        soilScoutScheduledDataImport.run();
        return ResponseEntity.ok().build();
    }

}
