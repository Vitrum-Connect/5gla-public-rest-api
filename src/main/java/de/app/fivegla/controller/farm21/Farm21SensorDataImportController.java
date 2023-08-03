package de.app.fivegla.controller.farm21;

import de.app.fivegla.api.BaseMappings;
import de.app.fivegla.integration.farm21.job.Farm21ScheduledSensorDataImport;
import de.app.fivegla.swagger.Tags;
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
@RequestMapping(BaseMappings.FARM_21 + "/import")
@Profile("manual-import-allowed")
public class Farm21SensorDataImportController {

    private final Farm21ScheduledSensorDataImport farm21ScheduledSensorDataImport;

    public Farm21SensorDataImportController(Farm21ScheduledSensorDataImport farm21ScheduledSensorDataImport) {
        this.farm21ScheduledSensorDataImport = farm21ScheduledSensorDataImport;
    }

    /**
     * Run the import.
     */
    @Operation(
            operationId = "sensor-data-import.run",
            description = "Run the import manually.",
            tags = {Tags.FARM_21}
    )
    @ApiResponse(
            responseCode = "200",
            description = "The import was started."
    )
    @PostMapping(value = "/run", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> runImport() {
        farm21ScheduledSensorDataImport.run();
        return ResponseEntity.ok().build();
    }

}
