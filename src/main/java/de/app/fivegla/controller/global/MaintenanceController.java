package de.app.fivegla.controller.global;


import de.app.fivegla.api.Response;
import de.app.fivegla.business.ThirdPartyApiConfigurationService;
import de.app.fivegla.config.security.marker.ApiKeyApiAccess;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.scheduled.DataImportScheduler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The MaintenanceController class handles maintenance-related operations.
 * It provides a method to send subscriptions for device measurement notifications.
 * <p>
 * This controller is mapped to the /maintenance endpoint.
 */
@Slf4j
@Profile("maintenance")
@RestController
@RequestMapping(BaseMappings.MAINTENANCE)
@RequiredArgsConstructor
public class MaintenanceController implements ApiKeyApiAccess {

    private final DataImportScheduler dataImportScheduler;

    /**
     * Run the import.
     */
    @Operation(
            operationId = "manual.import.run",
            description = "Run the import manually.",
            tags = BaseMappings.MAINTENANCE
    )
    @ApiResponse(
            responseCode = "200",
            description = "The import has been started asynchronously.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)
            )
    )
    @PostMapping(value = "/run", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> runAllImports() {
        dataImportScheduler.scheduleDataImport();
        return ResponseEntity.ok().body(new Response());
    }
}
