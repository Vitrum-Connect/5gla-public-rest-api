package de.app.fivegla.controller;

import de.app.fivegla.integration.soilscout.job.SoilScoutScheduledDataImport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

/**
 * Controller for information purpose.
 */
@RestController
@RequestMapping("/info")
public class InfoController {

    @Value("${app.version:unknown}")
    private String applicationVersion;

    private final SoilScoutScheduledDataImport soilScoutScheduledDataImport;

    public InfoController(SoilScoutScheduledDataImport soilScoutScheduledDataImport) {
        this.soilScoutScheduledDataImport = soilScoutScheduledDataImport;
    }

    /**
     * Returns the version of the application.
     *
     * @return the version of the application
     */
    @Operation(
            operationId = "info.version",
            description = "Fetch the version of the application."
    )
    @ApiResponse(
            responseCode = "200",
            description = "The version of the application.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE
            )
    )
    @GetMapping(value = "/version", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getVersion() {
        return ResponseEntity.ok(applicationVersion);
    }


    @Operation(
            operationId = "info.last-rum",
            description = "Fetch the last run of the import."
    )
    @ApiResponse(
            responseCode = "200",
            description = "The last run of the application.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE
            )
    )
    @GetMapping(value = "/last-run", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Instant> getLastImport() {
        return ResponseEntity.ok(soilScoutScheduledDataImport.getLastRun());
    }

}
