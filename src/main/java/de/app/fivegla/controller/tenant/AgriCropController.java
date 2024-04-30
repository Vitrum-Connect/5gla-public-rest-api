package de.app.fivegla.controller.tenant;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.Response;
import de.app.fivegla.business.AgriCropService;
import de.app.fivegla.config.security.marker.TenantCredentialApiAccess;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.persistence.ApplicationDataRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BaseMappings.AGRI_CROP)
public class AgriCropController implements TenantCredentialApiAccess {

    private final AgriCropService agriCropService;
    private final ApplicationDataRepository applicationDataRepository;

    @Operation(
            operationId = "agri-crop.import-geojson",
            description = "Imports the GeoJSON containing the agri-crop data.",
            tags = BaseMappings.AGRI_CROP
    )
    @ApiResponse(
            responseCode = "201",
            description = "The CSV was imported successfully.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "The request is invalid.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)
            )
    )
    @PostMapping(value = "/geo-json/{cropId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> importGeoJson(@RequestBody @Parameter(description = "The crop, represented as GeoJSON (RFC 7946).") String geoJson,
                                                            @PathVariable @Parameter(description = "The crop ID") String cropId,
                                                            Principal principal) {
        var optionalTenant = applicationDataRepository.getTenant(principal.getName());
        if (optionalTenant.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorMessage.builder()
                            .error(Error.TENANT_NOT_FOUND)
                            .message("No tenant found for id " + principal.getName())
                            .build());
        } else {
            var tenant = optionalTenant.get();
            agriCropService.createFromGeoJson(tenant, cropId, geoJson);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Response());
        }
    }

    @Operation(
            operationId = "agri-crop.import-csv",
            description = "Imports the CSV containing the agri-crop data.",
            tags = BaseMappings.AGRI_CROP
    )
    @ApiResponse(
            responseCode = "201",
            description = "The CSV was imported successfully.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "The request is invalid.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)
            )
    )
    @PostMapping(value = "/csv/{cropId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> importCsv(@RequestBody @Parameter(description = "The crop, represented as CSV") String csv,
                                                        @PathVariable @Parameter(description = "The crop ID") String cropId,
                                                        Principal principal) {
        var optionalTenant = applicationDataRepository.getTenant(principal.getName());
        if (optionalTenant.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorMessage.builder()
                            .error(Error.TENANT_NOT_FOUND)
                            .message("No tenant found for id " + principal.getName())
                            .build());
        } else {
            var tenant = optionalTenant.get();
            agriCropService.createFromCsv(tenant, cropId, csv);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Response());
        }
    }
}
