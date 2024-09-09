package de.app.fivegla.controller.tenant;

import de.app.fivegla.api.Response;
import de.app.fivegla.business.GroupService;
import de.app.fivegla.business.TenantService;
import de.app.fivegla.config.security.marker.TenantCredentialApiAccess;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.dto.request.GetAllImagesForTransactionRequest;
import de.app.fivegla.controller.dto.request.ImageProcessingRequest;
import de.app.fivegla.controller.dto.request.StationaryImageProcessingRequest;
import de.app.fivegla.controller.dto.response.*;
import de.app.fivegla.integration.imageprocessing.ImageProcessingIntegrationService;
import de.app.fivegla.integration.imageprocessing.OrthophotoIntegrationService;
import de.app.fivegla.persistence.entity.Image;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Controller for image processing.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BaseMappings.IMAGE_PROCESSING + "/images")
public class ImagesController implements TenantCredentialApiAccess {

    private final ImageProcessingIntegrationService imageProcessingIntegrationService;
    private final OrthophotoIntegrationService orthophotoIntegrationService;
    private final TenantService tenantService;
    private final GroupService groupService;

    @Value("${app.defaultSearchIntervalInDays}")
    private long defaultSearchIntervalInDays;

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
        var tenant = validateTenant(tenantService, principal);
        var group = groupService.getOrDefault(tenant, request.getGroupId());
        var oids = new ArrayList<String>();
        request.getImages().forEach(droneImage -> {
            var oid = imageProcessingIntegrationService.processImage(tenant, group, request.getTransactionId(), request.getCameraId(), droneImage.getImageChannel(), droneImage.getBase64Image());
            oids.add(oid);
        });
        return ResponseEntity.status(HttpStatus.CREATED).body(ImageProcessingResponse.builder()
                .oids(oids)
                .build());
    }

    /**
     * Processes one or multiple images from the mica sense camera.
     *
     * @return HTTP status 200 if image was processed successfully.
     */
    @Operation(
            operationId = "images.process-stationary-image",
            description = "Processes one or multiple stationary images from the mica sense camera.",
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
    @PostMapping(value = "/stationary", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> processImage(@Valid @RequestBody @Parameter(description = "The image processing request.", required = true) StationaryImageProcessingRequest request, Principal principal) {
        var tenant = validateTenant(tenantService, principal);
        var group = groupService.getOrDefault(tenant, request.getGroupId());
        var oids = new ArrayList<String>();
        request.getImages().forEach(droneImage -> {
            var oid = imageProcessingIntegrationService.processStationaryImage(tenant, group, request.getCameraId(), droneImage.getImageChannel(), droneImage.getBase64Image());
            oids.add(oid);
        });
        return ResponseEntity.status(HttpStatus.CREATED).body(ImageProcessingResponse.builder()
                .oids(oids)
                .build());
    }

    @GetMapping(value = "/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> listAllTransactionsForTenant(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Parameter(description = "Start of the search interval.") Instant from, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Parameter(description = "End of the search interval.") Instant to, Principal principal) {
        var tenant = validateTenant(tenantService, principal);
        if (to == null) {
            to = Instant.now().minus(defaultSearchIntervalInDays, ChronoUnit.DAYS);
        }
        var allTransactionsWithinTheTimeRange = imageProcessingIntegrationService.listAllTransactionsForTenant(from, to, tenant.getTenantId());
        var mappedValues = new HashMap<String, Instant>();
        allTransactionsWithinTheTimeRange.forEach(t -> mappedValues.put(t.transactionId(), t.timestampOfTheFirstImage()));
        var response = AllTransactionsForTenantResponse.builder()
                .from(from)
                .to(to)
                .transactionIdWithTheFirstImageTimestamp(mappedValues)
                .build();
        return ResponseEntity.ok(response);

    }

    /**
     * Return all images for a transaction.
     */
    @Operation(
            operationId = "images.get-all-images-for-transaction",
            description = "Returns all images for a transaction.",
            tags = BaseMappings.IMAGE_PROCESSING
    )
    @ApiResponse(
            responseCode = "200",
            description = "The images were found and returned.",
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
    @PostMapping(value = "/images-for-transaction", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> getAllImagesForTransaction(@Valid @RequestBody @Parameter(description = "The request to search for images.", required = true) GetAllImagesForTransactionRequest request, Principal principal) {
        var tenant = validateTenant(tenantService, principal);
        var images = imageProcessingIntegrationService.getAllImagesForTransaction(request.getTransactionId(), request.getChannel(), tenant.getTenantId());
        var imagesAsBase64EncodedData = images.stream().map(Image::getBase64encodedImage).toList();
        return ResponseEntity.ok(GetAllImagesForTransactionResponse.builder()
                .imagesAsBase64EncodedData(imagesAsBase64EncodedData)
                .build());
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
                    byte[] decodedImage = Base64.getDecoder().decode(image.getBase64encodedImage());
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

    /**
     * Triggers the orthophoto processing for the given transaction id.
     *
     * @param transactionId The transaction id.
     * @return The UUID of the triggered orthophoto processing.
     */
    @Operation(
            operationId = "images.calculate-combined-image",
            description = "Triggers the orthophoto processing for the given transaction id.",
            tags = BaseMappings.IMAGE_PROCESSING
    )
    @ApiResponse(
            responseCode = "201",
            description = "The orthophoto processing was triggered successfully.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = OrthphotoTriggeringResponse.class)
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
    @PostMapping(value = "/{transactionId}/calculate-combined-image", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> calculateCombinedImage(@PathVariable String transactionId, Principal principal) {
        validateTenant(tenantService, principal);
        var uuid = orthophotoIntegrationService.triggerOrthophotoProcessing(transactionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrthphotoTriggeringResponse.builder()
                .uuid(uuid)
                .build());
    }

    @Operation(
            operationId = "images.download-combined-image",
            description = "Downloads the combined image for the given transaction id.",
            tags = BaseMappings.IMAGE_PROCESSING
    )
    @ApiResponse(
            responseCode = "200",
            description = "The combined image was found and returned.",
            content = @Content(
                    mediaType = "application/zip",
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
    @GetMapping(value = "/{transactionId}/combined-image", produces = "application/zip")
    public ResponseEntity<byte[]> downloadCombinedImage(@PathVariable String transactionId, Principal principal) {
        var tenant = validateTenant(tenantService, principal);
        var headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        var orthophoto = orthophotoIntegrationService.getOrthophoto(tenant, transactionId);
        AtomicReference<ResponseEntity<byte[]>> responseEntity = new AtomicReference<>(ResponseEntity.notFound().build());
        orthophoto.ifPresent(
                image -> {
                    var resultFileAsZip = orthophoto.get();
                    headers.setContentLength(resultFileAsZip.length);
                    responseEntity.set(ResponseEntity.ok().headers(headers).body(resultFileAsZip));
                }
        );
        return responseEntity.get();
    }

    @Operation(
            operationId = "images.mark-as-processed",
            description = "Marks the transaction with the given ID as processed.",
            tags = BaseMappings.IMAGE_PROCESSING
    )
    @ApiResponse(
            responseCode = "200",
            description = "The transaction was marked as processed.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE
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
    @PostMapping(value = "/{transactionId}/mark-as-processed", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> markImageAsProcessed(@PathVariable String transactionId, Principal principal) {
        var tenant = validateTenant(tenantService, principal);
        log.info("Marking transaction with id {} as processed.", transactionId);
        log.debug("Tenant: {}", tenant);
        imageProcessingIntegrationService.markTransactionAsProcessed(transactionId);
        return ResponseEntity.ok().build();
    }

}
