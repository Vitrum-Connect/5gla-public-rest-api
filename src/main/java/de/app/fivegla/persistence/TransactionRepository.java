package de.app.fivegla.persistence;

import de.app.fivegla.persistence.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for the transaction entity.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Find a transaction by its transaction id.
     *
     * @param transactionId The transaction id.
     * @return The transaction with the given transaction id.
     */
    Optional<Transaction> findByTransactionId(String transactionId);
}
