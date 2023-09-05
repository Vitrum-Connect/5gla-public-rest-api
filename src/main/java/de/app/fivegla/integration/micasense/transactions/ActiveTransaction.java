package de.app.fivegla.integration.micasense.transactions;

import java.util.List;

/**
 * Active transactions.
 *
 * @param transactionId The transaction id.
 * @param droneId       The id of the drone.
 * @Param imageOids     The image oids.
 */
public record ActiveTransaction(String transactionId, String droneId, List<String> imageOids) {
}
