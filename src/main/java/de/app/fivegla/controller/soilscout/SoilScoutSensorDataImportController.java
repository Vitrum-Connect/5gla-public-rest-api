package de.app.fivegla.controller.soilscout;

import de.app.fivegla.api.BaseMappings;
import de.app.fivegla.integration.soilscout.job.SoilScoutScheduledMeasurementImport;
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
@RequestMapping(BaseMappings.SOIL_SCOUT + "/import")
@Profile("manual-import-allowed")
public class SoilScoutSensorDataImportController {

    private final SoilScoutScheduledMeasurementImport soilScoutScheduledMeasurementImport;

    public SoilScoutSensorDataImportController(SoilScoutScheduledMeasurementImport soilScoutScheduledMeasurementImport) {
        this.soilScoutScheduledMeasurementImport = soilScoutScheduledMeasurementImport;
    }

    /**
     * Run the import.
     */
    @Operation(
            operationId = "sensor-data-import.run",
            description = "Run the import manually.",
            tags = BaseMappings.SOIL_SCOUT
    )
    @ApiResponse(
            responseCode = "200",
            description = "The import was started."
    )
    @PostMapping(value = "/run", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> runImport() {
        soilScoutScheduledMeasurementImport.run();
        return ResponseEntity.ok().build();
    }

}
