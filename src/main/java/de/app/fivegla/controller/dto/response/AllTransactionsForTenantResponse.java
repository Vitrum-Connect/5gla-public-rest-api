package de.app.fivegla.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.connector.Response;

import java.time.Instant;
import java.util.Map;

/**
 * Response class for retrieving all transactions for a tenant.
 * Extends the base Response class.
 */
@Getter
@Setter
@Builder
@Schema(description = "Response to get all transactions for a tenant.")
public class AllTransactionsForTenantResponse extends Response {

    /**
     * The start of the search interval.
     */
    @Schema(description = "The start of the search interval.")
    private Instant from;

    /**
     * The end of the search interval.
     */
    @Schema(description = "The end of the search interval.")
    private Instant to;

    /**
     * The transaction id with the first image timestamp.
     */
    @Schema(description = "The transaction id with the first image timestamp.")
    private Map<String, Instant> transactionIdWithTheFirstImageTimestamp;
}
