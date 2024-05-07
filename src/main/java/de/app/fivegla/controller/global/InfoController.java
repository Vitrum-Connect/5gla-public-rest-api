package de.app.fivegla.controller.global;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.api.Response;
import de.app.fivegla.config.security.marker.ApiKeyApiAccess;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.dto.response.FiwareStatusResponse;
import de.app.fivegla.controller.dto.response.LastRunResponse;
import de.app.fivegla.controller.dto.response.VersionResponse;
import de.app.fivegla.integration.fiware.StatusIntegrationService;
import de.app.fivegla.persistence.ApplicationDataRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * Controller for information purpose.
 */
@RestController
@RequestMapping(BaseMappings.INFO)
public class InfoController implements ApiKeyApiAccess {

    @Value("${app.version:unknown}")
    private String applicationVersion;

    private final ApplicationDataRepository applicationDataRepository;
    private final StatusIntegrationService statusIntegrationService;

    public InfoController(ApplicationDataRepository applicationDataRepository,
                          StatusIntegrationService statusIntegrationService) {
        this.applicationDataRepository = applicationDataRepository;
        this.statusIntegrationService = statusIntegrationService;
    }

    /**
     * Returns the version of the application.
     *
     * @return the version of the application
     */
    @Operation(
            operationId = "info.version",
            description = "Fetch the version of the application.",
            tags = BaseMappings.INFO
    )
    @ApiResponse(
            responseCode = "200",
            description = "The version of the application.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = VersionResponse.class)
            )
    )
    @GetMapping(value = "/version", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> getVersion() {
        return ResponseEntity.ok(VersionResponse.builder().version(applicationVersion).build());
    }

    /**
     * Returns the status of the fiware connection.
     *
     * @return the status of the fiware connection
     */
    @Operation(
            operationId = "info.fiware",
            description = "Fetch the status of the fiware connection.",
            tags = BaseMappings.INFO
    )
    @ApiResponse(
            responseCode = "200",
            description = "The status of the fiware connection.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = FiwareStatusResponse.class)
            )
    )
    @GetMapping(value = "/fiware", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> getFiwareStatus() {
        var version = statusIntegrationService.getVersion();
        return ResponseEntity.ok(FiwareStatusResponse.builder()
                .fiwareStatus(HttpStatus.OK)
                .fiwareVersion(version)
                .build());
    }


    /**
     * Returns the last run of the import.
     *
     * @return the last run of the import
     */
    @Operation(
            operationId = "info.last-rum",
            description = "Fetch the last run of the import.",
            tags = BaseMappings.INFO
    )
    @ApiResponse(
            responseCode = "200",
            description = "The last run of the application.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = LastRunResponse.class)
            )
    )
    @GetMapping(value = "/last-run", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> getLastImport() {
        var lastRuns = new HashMap<Manufacturer, String>();
        var savedLastRuns = applicationDataRepository.getLastRuns();
        if (null != savedLastRuns) {
            savedLastRuns
                    .forEach((key, value) -> lastRuns.put(key, DateTimeFormatter.ISO_INSTANT.format(value)));
        }
        return ResponseEntity.ok(LastRunResponse.builder().lastRuns(lastRuns).build());
    }

}
