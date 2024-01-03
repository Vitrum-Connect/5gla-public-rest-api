package de.app.fivegla.controller;

import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.api.swagger.OperationTags;
import de.app.fivegla.controller.dto.request.DisableJobRequest;
import de.app.fivegla.persistence.ApplicationDataRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The JobController class handles requests related to job operations.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BaseMappings.JOB)
public class JobController {

    private final ApplicationDataRepository applicationDataRepository;

    /**
     * Disables a job for a manufacturer.
     *
     * @param request The request to disable a job.
     * @return A ResponseEntity indicating the success of the job disabling.
     */
    @Operation(
            operationId = "job.disable",
            description = "Disables a job for a manufacturer.",
            tags = OperationTags.JOB
    )
    @ApiResponse(
            responseCode = "200",
            description = "The job has been disabled successfully."
    )
    @ApiResponse(
            responseCode = "400",
            description = "The request is invalid."
    )
    @PatchMapping
    public ResponseEntity<Void> disable(@RequestBody DisableJobRequest request) {
        log.debug("Disabling job for manufacturer: {}", request.getManufacturer());
        applicationDataRepository.disableJob(request.getManufacturer());
        return ResponseEntity.ok().build();
    }

}
