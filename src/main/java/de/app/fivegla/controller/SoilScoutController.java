package de.app.fivegla.controller;

import de.app.fivegla.api.Tags;
import de.app.fivegla.controller.dto.SoilScoutSensorResponse;
import de.app.fivegla.integration.soilscout.SoilScoutIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/soil-scout")
public class SoilScoutController {

    private final SoilScoutIntegrationService soilScoutIntegrationService;

    public SoilScoutController(SoilScoutIntegrationService soilScoutIntegrationService) {
        this.soilScoutIntegrationService = soilScoutIntegrationService;
    }

    /**
     * List all soil scout sensors.
     *
     * @return all soil scout sensors
     */
    @Operation(
            operationId = "soil-scout.find-all",
            description = "Fetch all soil scout sensors.",
            tags = {Tags.SOIL_SCOUT}
    )
    @ApiResponse(
            responseCode = "200",
            description = "All soil scout sensors.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = SoilScoutSensorResponse.class)
            )
    )
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SoilScoutSensorResponse> findAll() {
        return ResponseEntity.ok(SoilScoutSensorResponse.builder().sensors(soilScoutIntegrationService.findAllSensors()).build());
    }

}
