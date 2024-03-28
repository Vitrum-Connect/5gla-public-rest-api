package de.app.fivegla.controller.global;


import de.app.fivegla.api.SubscriptionStatus;
import de.app.fivegla.api.enums.MeasurementType;
import de.app.fivegla.config.security.marker.ApiKeyApiAccess;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.fiware.SubscriptionService;
import de.app.fivegla.scheduled.DataImportScheduler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * The MaintenanceController class handles maintenance-related operations.
 * It provides a method to send subscriptions for device measurement notifications.
 * <p>
 * This controller is mapped to the /maintenance endpoint.
 */
@Slf4j
@Profile("maintenance")
@RestController
@RequestMapping(BaseMappings.MAINTENANCE)
@RequiredArgsConstructor
public class MaintenanceController implements ApiKeyApiAccess {

    private final SubscriptionService subscriptionService;
    private final DataImportScheduler dataImportScheduler;
    private final SubscriptionStatus subscriptionStatus;


    /**
     * Sends a subscription for device measurement notifications.
     *
     * @return A ResponseEntity object with HTTP status code and an empty body.
     */
    @Operation(
            operationId = "maintenance.send-subscription",
            description = "Sends a subscription for device measurement notifications.",
            tags = BaseMappings.MAINTENANCE
    )
    @ApiResponse(
            responseCode = "200",
            description = "The subscription was sent successfully."
    )
    @ApiResponse(
            responseCode = "400",
            description = "The request is invalid since the subscriptions are disabled."
    )
    @PostMapping("/send-subscription")
    public ResponseEntity<Void> sendSubscription() {
        if (subscriptionStatus.isSubscriptionsEnabled()) {
            Arrays.stream(MeasurementType.values()).forEach(type -> subscriptionService.removeAll(type.name()));
            subscriptionService.subscribe(Arrays.stream(MeasurementType.values()).map(Enum::name).toArray(String[]::new));
            log.info("Subscribed to device measurement notifications.");
            subscriptionStatus.setSubscriptionsSent(true);
            return ResponseEntity.ok().build();
        } else {
            log.warn("Subscriptions are disabled. Not subscribing to device measurement notifications.");
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Run the import.
     */
    @Operation(
            operationId = "manual.import.run",
            description = "Run the import manually.",
            tags = BaseMappings.MAINTENANCE
    )
    @ApiResponse(
            responseCode = "200",
            description = "The import has been started asynchronously."
    )
    @PostMapping(value = "/run")
    public ResponseEntity<Void> runAllImports() {
        dataImportScheduler.scheduleDataImport();
        return ResponseEntity.ok().build();
    }

}
