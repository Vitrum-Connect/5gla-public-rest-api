package de.app.fivegla.controller;

import de.app.fivegla.business.AgriCropService;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.api.swagger.OperationTags;
import de.app.fivegla.controller.security.SecuredApiAccess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BaseMappings.AGRI_CROP)
public class AgriCropController implements SecuredApiAccess {

    private final AgriCropService agriCropService;

    @Operation(
            operationId = "agri-crop.import-geojson",
            description = "Imports the GeoJSON file containing the agri-crop data.",
            tags = OperationTags.AGRI_CROP
    )
    @ApiResponse(
            responseCode = "201",
            description = "The GeoJSON file was imported successfully."
    )
    @ApiResponse(
            responseCode = "400",
            description = "The request is invalid."
    )
    @PostMapping("/geo-json/feature")
    public ResponseEntity<Void> importGeoJson(@RequestBody @Parameter(description = "The crop, represented as GeoJSON (RFC 7946).") String geoJson) {
        var feature = agriCropService.parseFeature(geoJson);
        log.debug("Parsed feature: {}.", feature);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
