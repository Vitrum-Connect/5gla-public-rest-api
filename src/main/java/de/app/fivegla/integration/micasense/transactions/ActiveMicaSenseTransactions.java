package de.app.fivegla.integration.micasense.transactions;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Active transactions.
 */
@Getter
@Component
public class ActiveMicaSenseTransactions {

    /**
     * The active transactions.
     */
    private final Map<String, ActiveTransaction> activeTransactions;

    public ActiveMicaSenseTransactions() {
        activeTransactions = new HashMap<>();
    }

    /**
     * Adds a new transaction to the activeTransactions map with the given transactionId, droneId, and oid.
     * If the activeTransactions map does not contain the transactionId, a new ActiveTransaction object is created
     * and added to the map. Then the oid is added to the imageOids list of the corresponding ActiveTransaction object.
     *
     * @param transactionId the id of the transaction
     * @param droneId       the id of the drone
     * @param oid           the id of the image
     */
    public void add(String transactionId, String droneId, String oid) {
        if (!activeTransactions.containsKey(transactionId)) {
            activeTransactions.put(transactionId, new ActiveTransaction(transactionId, droneId, new ArrayList<>()));
        }
        activeTransactions.get(transactionId).imageOids().add(oid);
    }
}
