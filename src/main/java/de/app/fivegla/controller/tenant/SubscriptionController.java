package de.app.fivegla.controller.tenant;

import de.app.fivegla.api.Response;
import de.app.fivegla.business.SubscriptionService;
import de.app.fivegla.business.TenantService;
import de.app.fivegla.config.security.marker.TenantCredentialApiAccess;
import de.app.fivegla.controller.api.BaseMappings;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * The SubscriptionController class handles subscription-related operations.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BaseMappings.SUBSCRIPTION)
public class SubscriptionController implements TenantCredentialApiAccess {

    private final TenantService tenantService;
    private final SubscriptionService subscriptionService;

    @Operation(
            operationId = "delete.all.subscriptions",
            description = "Delete all subscriptions.",
            tags = BaseMappings.SUBSCRIPTION
    )
    @ApiResponse(
            responseCode = "200",
            description = "All subscriptions have been deleted.",
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
    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> deleteAllSubscriptions(Principal principal) {
        var tenant = validateTenant(tenantService, principal);
        subscriptionService.deleteAllSubscriptions(tenant);
        return ResponseEntity.ok().body(new Response());
    }

}
