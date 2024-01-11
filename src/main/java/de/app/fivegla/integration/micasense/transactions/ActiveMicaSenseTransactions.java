package de.app.fivegla.integration.micasense.transactions;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.micasense.events.ImageProcessingFinishedEvent;
import de.app.fivegla.integration.micasense.events.ImageProcessingStartedEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Active transactions.
 */
@Slf4j
@Getter
@Component
@RequiredArgsConstructor
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
     * @param oid           the id of the image
     */
    public void add(String transactionId, String oid) {
        if (!activeTransactions.containsKey(transactionId)) {
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.MICASENSE_TX_NOT_FOUND)
                    .message("Transaction with id " + transactionId + " does not exist.")
                    .build()
            );
        }
        activeTransactions.get(transactionId).imageOids().add(oid);
    }

    /**
     * Adds a new active image processing transaction to the map of active transactions.
     *
     * @param event The ImageProcessingStartedEvent containing the transaction information.
     */
    @EventListener(ImageProcessingStartedEvent.class)
    public void addNewActiveImageProcessingTransaction(ImageProcessingStartedEvent event) {
        log.debug("Adding new active transaction with id {} for drone {}.", event.getTransactionId(), event.getDroneId());
        activeTransactions.put(event.getTransactionId(), new ActiveTransaction(event.getTransactionId(), event.getDroneId(), new ArrayList<>()));
    }

    /**
     * Removes an active image processing transaction from the map of active transactions.
     *
     * @param event The ImageProcessingFinishedEvent containing the transaction information.
     */
    @EventListener(ImageProcessingFinishedEvent.class)
    public void removeActiveImageProcessingTransaction(ImageProcessingFinishedEvent event) {
        log.debug("Removing active transaction with id {} for drone {}.", event.getTransactionId(), event.getDroneId());
        activeTransactions.remove(event.getTransactionId());
    }

}
