package de.app.fivegla.controller.manual_import;

import de.app.fivegla.api.BaseMappings;
import de.app.fivegla.controller.swagger.OperationTags;
import de.app.fivegla.integration.agranimo.job.AgranimoScheduledMeasurementImport;
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
@RequestMapping(BaseMappings.AGRANIMO + "/import")
@Profile("manual-import-allowed")
public class AgranimoSensorDataImportController {

    private final AgranimoScheduledMeasurementImport agranimoScheduledMeasurementImport;

    public AgranimoSensorDataImportController(AgranimoScheduledMeasurementImport agranimoScheduledMeasurementImport) {
        this.agranimoScheduledMeasurementImport = agranimoScheduledMeasurementImport;
    }

    /**
     * Run the import.
     */
    @Operation(
            operationId = "agranimo.import.run",
            description = "Run the import manually.",
            tags = OperationTags.MANUAL_IMPORT
    )
    @ApiResponse(
            responseCode = "200",
            description = "The import was started."
    )
    @PostMapping(value = "/run", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> runImport() {
        agranimoScheduledMeasurementImport.run();
        return ResponseEntity.ok().build();
    }

}
