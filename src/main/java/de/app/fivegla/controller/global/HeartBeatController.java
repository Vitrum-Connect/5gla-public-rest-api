package de.app.fivegla.controller.global;

import de.app.fivegla.business.HeartBeatService;
import de.app.fivegla.config.security.marker.ApiKeyApiAccess;
import de.app.fivegla.controller.api.BaseMappings;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The HeartBeatController class handles heartbeat-related operations.
 * It provides a method to register a heartbeat.
 * <p>
 * This controller is mapped to the /heartbeat endpoint.
 */
@Slf4j
@RestController
@RequestMapping(BaseMappings.HEARTBEAT)
@RequiredArgsConstructor
public class HeartBeatController implements ApiKeyApiAccess {

    private final HeartBeatService heartBeatService;

    @Operation(
            operationId = "heartbeat",
            description = "Heartbeat endpoint.",
            tags = BaseMappings.HEARTBEAT
    )
    @ApiResponse(
            responseCode = "200",
            description = "The heartbeat was successful."
    )
    @PostMapping("/{id}")
    public ResponseEntity<Void> heartBeat(@PathVariable @Schema(description = "The ID") String id) {
        heartBeatService.registerHeartBeat(id);
        return ResponseEntity.ok().build();
    }

}
