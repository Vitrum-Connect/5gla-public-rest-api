package de.app.fivegla.controller;

import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.api.swagger.OperationTags;
import de.app.fivegla.controller.security.SecuredApiAccess;
import de.app.fivegla.scheduled.DataImportScheduler;
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
@RequestMapping(BaseMappings.MANUAL_IMPORT + "/import")
@Profile("manual-import-allowed")
public class SensorDataImportController implements SecuredApiAccess {

    private final DataImportScheduler dataImportScheduler;

    public SensorDataImportController(DataImportScheduler dataImportScheduler) {
        this.dataImportScheduler = dataImportScheduler;
    }

    /**
     * Run the import.
     */
    @Operation(
            operationId = "manual.import.run",
            description = "Run the import manually.",
            tags = OperationTags.MANUAL_IMPORT
    )
    @ApiResponse(
            responseCode = "200",
            description = "The import has been started asynchronously."
    )
    @PostMapping(value = "/run", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> runAllImports() {
        dataImportScheduler.scheduleDataImport();
        return ResponseEntity.ok().build();
    }

}
