package de.app.fivegla.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "transaction_state")
public class Transaction extends BaseEntity {

    /**
     * The transaction id.
     */
    private String transactionId;

    /**
     * The transaction state.
     */
    @Enumerated(EnumType.STRING)
    private TransactionState transactionState;

    /**
     * The tenant.
     */
    @ManyToOne
    private Tenant tenant;

    /**
     * Marks the transaction as active.
     */
    public void markAsActive() {
        this.transactionState = TransactionState.ACTIVE;
    }

    /**
     * Marks the transaction as processed.
     */
    public void markAsProcessed() {
        this.transactionState = TransactionState.PROCESSED;
    }

    /**
     * Checks if the transaction is active.
     *
     * @return True if the transaction is active, false otherwise.
     */
    public boolean isProcessed() {
        return this.transactionState == TransactionState.PROCESSED;
    }

    public enum TransactionState {
        ACTIVE, PROCESSED
    }
}
