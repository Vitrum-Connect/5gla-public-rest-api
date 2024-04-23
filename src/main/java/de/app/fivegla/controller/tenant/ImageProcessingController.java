package de.app.fivegla.controller.tenant;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.Response;
import de.app.fivegla.config.security.marker.TenantCredentialApiAccess;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.dto.request.ImageProcessingRequest;
import de.app.fivegla.controller.dto.response.ImageProcessingResponse;
import de.app.fivegla.controller.dto.response.OidsForTransactionResponse;
import de.app.fivegla.integration.micasense.ImageProcessingIntegrationService;
import de.app.fivegla.persistence.ApplicationDataRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Controller for image processing.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BaseMappings.IMAGE_PROCESSING + "/images")
public class ImageProcessingController implements TenantCredentialApiAccess {

    private final ImageProcessingIntegrationService imageProcessingIntegrationService;
    private final ApplicationDataRepository applicationDataRepository;

    /**
     * Processes one or multiple images from the mica sense camera.
     *
     * @return HTTP status 200 if image was processed successfully.
     */
    @Operation(
            operationId = "images.process-image",
            description = "Processes one or multiple images from the mica sense camera.",
            tags = BaseMappings.IMAGE_PROCESSING
    )
    @ApiResponse(
            responseCode = "201",
            description = "Images were processed successfully.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ImageProcessingResponse.class)
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
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> processImage(@Valid @RequestBody @Parameter(description = "The image processing request.", required = true) ImageProcessingRequest request, Principal principal) {
        log.debug("Processing image for the drone: {}.", request.getDroneId());
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
            var oids = new ArrayList<String>();
            request.getImages().forEach(droneImage -> {
                var oid = imageProcessingIntegrationService.processImage(tenant, request.getTransactionId(), request.getDroneId(), droneImage.getMicaSenseChannel(), droneImage.getBase64Image());
                oids.add(oid);
            });
            return ResponseEntity.status(HttpStatus.CREATED).body(ImageProcessingResponse.builder()
                    .oids(oids)
                    .build());
        }
    }

    /**
     * Return image as stream.
     */
    @Operation(
            operationId = "images.get-image",
            description = "Returns an image from the mica sense camera stored in the database.",
            tags = BaseMappings.IMAGE_PROCESSING
    )
    @ApiResponse(
            responseCode = "200",
            description = "The image was found and returned.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = byte[].class)
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
    @GetMapping(value = "/{oid}", produces = "image/tiff")
    public ResponseEntity<byte[]> getImage(@PathVariable String oid) {
        var headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        var micaSenseImage = imageProcessingIntegrationService.getImage(oid);
        AtomicReference<ResponseEntity<byte[]>> responseEntity = new AtomicReference<>(ResponseEntity.notFound().build());
        micaSenseImage.ifPresent(
                image -> {
                    byte[] decodedImage = Base64.getDecoder().decode(image.getBase64Image());
                    headers.setContentLength(decodedImage.length);
                    responseEntity.set(ResponseEntity.ok().headers(headers).body(decodedImage));
                }
        );
        return responseEntity.get();
    }

    /**
     * Return the image Object IDs (Oids) associated with a specific transaction.
     *
     * @param transactionId The ID of the transaction
     * @return The ResponseEntity with the OIDs for the images
     */
    @Operation(
            operationId = "images.get-image-oids-for-transaction",
            description = "Returns the image Object IDs (Oids) associated with a specific transaction.",
            tags = BaseMappings.IMAGE_PROCESSING
    )
    @ApiResponse(
            responseCode = "200",
            description = "The OIDs for the images were found and returned.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = OidsForTransactionResponse.class)
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
    @GetMapping(value = "/{transactionId}/oids", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> getImageOidsForTransaction(@PathVariable String transactionId) {
        var oids = imageProcessingIntegrationService.getImageOidsForTransaction(transactionId);
        return ResponseEntity.ok(OidsForTransactionResponse.builder()
                .transactionId(transactionId)
                .oids(oids)
                .build());
    }

}
