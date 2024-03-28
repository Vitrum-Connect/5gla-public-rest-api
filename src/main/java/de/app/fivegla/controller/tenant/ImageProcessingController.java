package de.app.fivegla.controller.tenant;

import de.app.fivegla.config.security.marker.TenantCredentialApiAccess;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.api.swagger.OperationTags;
import de.app.fivegla.controller.dto.request.ImageProcessingRequest;
import de.app.fivegla.controller.dto.response.ImageProcessingResponse;
import de.app.fivegla.controller.dto.response.OidsForTransactionResponse;
import de.app.fivegla.integration.micasense.ImageProcessingIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Controller for image processing.
 */
@Slf4j
@RestController
@RequestMapping(BaseMappings.IMAGE_PROCESSING + "/images")
public class ImageProcessingController implements TenantCredentialApiAccess {

    private final ImageProcessingIntegrationService imageProcessingIntegrationService;

    public ImageProcessingController(ImageProcessingIntegrationService imageProcessingIntegrationService) {
        this.imageProcessingIntegrationService = imageProcessingIntegrationService;
    }

    /**
     * Processes one or multiple images from the mica sense camera.
     *
     * @return HTTP status 200 if image was processed successfully.
     */
    @Operation(
            operationId = "images.process-image",
            description = "Processes one or multiple images from the mica sense camera.",
            tags = OperationTags.IMAGE_PROCESSING
    )
    @ApiResponse(
            responseCode = "201",
            description = "Images were processed successfully."
    )
    @ApiResponse(
            responseCode = "400",
            description = "The request is invalid."
    )
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImageProcessingResponse> processImage(@Valid @RequestBody @Parameter(description = "The image processing request.", required = true) ImageProcessingRequest request) {
        log.debug("Processing image for the drone: {}.", request.getDroneId());
        var oids = new ArrayList<String>();
        request.getImages().forEach(droneImage -> {
            var oid = imageProcessingIntegrationService.processImage(request.getTransactionId(), request.getDroneId(), droneImage.getMicaSenseChannel(), droneImage.getBase64Image());
            oids.add(oid);
        });
        return ResponseEntity.status(HttpStatus.CREATED).body(ImageProcessingResponse.builder()
                .oids(oids)
                .build());
    }

    /**
     * Begins the image processing for the transaction.
     *
     * @param droneId       The drone ID. (required)
     * @param transactionId The transaction ID. (required)
     * @return A ResponseEntity object with a status of 201.
     */
    @Operation(
            operationId = "images.begin-image-processing",
            description = "Begins the image processing for the transaction.",
            tags = OperationTags.IMAGE_PROCESSING
    )
    @ApiResponse(
            responseCode = "201",
            description = "The image processing was begun."
    )
    @PostMapping(value = "/{droneId}/{transactionId}/begin")
    public ResponseEntity<Void> beginImageProcessing(@PathVariable @Parameter(description = "The drone ID.", required = true) String droneId,
                                                     @PathVariable @Parameter(description = "The transaction ID.", required = true) String transactionId) {
        log.debug("Beginning image processing for the transaction: {}.", transactionId);
        imageProcessingIntegrationService.beginImageProcessing(droneId, transactionId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Ends the image processing for the transaction.
     *
     * @param droneId       the ID of the drone to end the image processing for
     * @param transactionId the ID of the transaction to end the image processing for
     * @return HTTP status 200 if the image processing was ended successfully
     */
    @Operation(
            operationId = "images.end-image-processing",
            description = "Ends the image processing for the transaction.",
            tags = OperationTags.IMAGE_PROCESSING
    )
    @ApiResponse(
            responseCode = "201",
            description = "The image processing was ended."
    )
    @PostMapping(value = "/{droneId}/{transactionId}/end")
    public ResponseEntity<Void> endImageProcessing(@PathVariable @Parameter(description = "The drone ID.", required = true) String droneId,
                                                   @PathVariable @Parameter(description = "The transaction ID.", required = true) String transactionId) {
        log.debug("Ending image processing for the transaction: {}.", transactionId);
        imageProcessingIntegrationService.endImageProcessing(droneId, transactionId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Return image as stream.
     */
    @Operation(
            operationId = "images.get-image",
            description = "Returns an image from the mica sense camera stored in the database.",
            tags = OperationTags.IMAGE_PROCESSING
    )
    @ApiResponse(
            responseCode = "200",
            description = "The image was found and returned."
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
            tags = OperationTags.IMAGE_PROCESSING
    )
    @ApiResponse(
            responseCode = "200",
            description = "The OIDs for the images were found and returned."
    )
    @GetMapping(value = "/{transactionId}/oids", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OidsForTransactionResponse> getImageOidsForTransaction(@PathVariable String transactionId) {
        var oids = imageProcessingIntegrationService.getImageOidsForTransaction(transactionId);
        return ResponseEntity.ok(OidsForTransactionResponse.builder()
                .transactionId(transactionId)
                .oids(oids)
                .build());
    }

}
