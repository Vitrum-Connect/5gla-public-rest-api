package de.app.fivegla.controller;

import de.app.fivegla.controller.dto.LastRunResponse;
import de.app.fivegla.controller.dto.VersionResponse;
import de.app.fivegla.persistence.ApplicationDataRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;

/**
 * Controller for information purpose.
 */
@RestController
@RequestMapping("/info")
public class InfoController {

    @Value("${app.version:unknown}")
    private String applicationVersion;

    private final ApplicationDataRepository applicationDataRepository;

    public InfoController(ApplicationDataRepository applicationDataRepository) {
        this.applicationDataRepository = applicationDataRepository;
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
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = VersionResponse.class)
            )
    )
    @GetMapping(value = "/version", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VersionResponse> getVersion() {
        return ResponseEntity.ok(VersionResponse.builder().version(applicationVersion).build());
    }


    /**
     * Returns the last run of the import.
     *
     * @return the last run of the import
     */
    @Operation(
            operationId = "info.last-rum",
            description = "Fetch the last run of the import."
    )
    @ApiResponse(
            responseCode = "200",
            description = "The last run of the application.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = LastRunResponse.class)
            )
    )
    @GetMapping(value = "/last-run", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LastRunResponse> getLastImport() {
        var lastRun = applicationDataRepository.getLastRun() != null ? DateTimeFormatter.ISO_INSTANT.format(applicationDataRepository.getLastRun()) : null;
        return ResponseEntity.ok(LastRunResponse.builder().lastRun(lastRun).build());
    }

}
