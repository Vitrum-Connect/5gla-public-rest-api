package de.app.fivegla.integration.imageprocessing.dto;


import java.time.Instant;

/**
 * The transaction id with the timestamp of the first image.
 *
 * @param transactionId            The transaction id.
 * @param timestampOfTheFirstImage The timestamp of the first image.
 */
public record TransactionIdWithTheFirstImageTimestamp(String transactionId, Instant timestampOfTheFirstImage) {
}
