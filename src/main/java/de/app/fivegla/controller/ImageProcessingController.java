package de.app.fivegla.controller;

import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.dto.request.ImageProcessingRequest;
import de.app.fivegla.controller.dto.response.ImageProcessingResponse;
import de.app.fivegla.controller.api.swagger.OperationTags;
import de.app.fivegla.integration.micasense.MicaSenseIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Controller for image processing.
 */
@Slf4j
@RestController
@RequestMapping(BaseMappings.MICA_SENSE + "/images")
public class ImageProcessingController {

    private final MicaSenseIntegrationService micaSenseIntegrationService;

    public ImageProcessingController(MicaSenseIntegrationService micaSenseIntegrationService) {
        this.micaSenseIntegrationService = micaSenseIntegrationService;
    }

    /**
     * Processes one or multiple images from the mica sense camera.
     *
     * @return 200 if image was processed successfully.
     */
    @Operation(
            operationId = "images.process-image",
            description = "Processes one or multiple images from the mica sense camera.",
            tags = OperationTags.MICA_SENSE
    )
    @ApiResponse(
            responseCode = "200",
            description = "Images were processed successfully."
    )
    @ApiResponse(
            responseCode = "400",
            description = "The request is invalid."
    )
    @PostMapping(value = "/", consumes = "application/json")
    public ResponseEntity<ImageProcessingResponse> processImage(@Valid @RequestBody ImageProcessingRequest request) {
        log.debug("Processing image for the drone: {}.", request.getDroneId());
        var oids = new ArrayList<String>();
        request.getImages().forEach(droneImage -> {
            var oid = micaSenseIntegrationService.processImage(request.getDroneId(), droneImage.getMicaSenseChannel(), droneImage.getBase64Image());
            oids.add(oid);
        });
        return ResponseEntity.ok(ImageProcessingResponse.builder()
                .oids(oids)
                .build());
    }

    /**
     * Return image as stream.
     */
    @Operation(
            operationId = "images.get-image",
            description = "Returns an image from the mica sense camera stored in the database.",
            tags = OperationTags.MICA_SENSE
    )
    @ApiResponse(
            responseCode = "200",
            description = "The image was found and returned."
    )
    @GetMapping(value = "/{oid}", produces = "image/tiff")
    public ResponseEntity<byte[]> getImage(@PathVariable String oid) {
        var headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        var micaSenseImage = micaSenseIntegrationService.getImage(oid);
        AtomicReference<ResponseEntity<byte[]>> responseEntity = new AtomicReference<>(ResponseEntity.notFound().build());
        micaSenseImage.ifPresent(
                image -> {
                    headers.setContentLength(image.length);
                    responseEntity.set(ResponseEntity.ok().headers(headers).body(image));
                }
        );
        return responseEntity.get();
    }

}
