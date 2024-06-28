package de.app.fivegla.persistence;

import de.app.fivegla.persistence.entity.Group;
import de.app.fivegla.persistence.entity.Image;
import de.app.fivegla.persistence.entity.enums.ImageChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Repository for the image entity.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    /**
     * Find an image by its oid.
     *
     * @param oid The oid of the image.
     * @return The image with the given oid.
     */
    Optional<Image> findByOid(String oid);

    /**
     * Find images by their transaction id.
     *
     * @param transactionId The transaction id.
     * @return The images with the given transaction id.
     */
    List<Image> findByTransactionId(String transactionId);

    /**
     * Deletes image entity by tenant id.
     *
     * @param tenantId The id of the tenant.
     */
    void deleteByTenantTenantId(String tenantId);

    /**
     * Finds images by group.
     *
     * @param group The group to filter the images by.
     */
    List<Image> findByGroup(Group group);

    /**
     * Finds images by transaction id and channel.
     *
     * @param transactionId The transaction id.
     * @param channel       The channel of the image.
     * @param tenantId      The id of the tenant.
     * @return The images with the given transaction id and channel.
     */
    List<Image> findByTransactionIdAndChannelAndTenantTenantId(String transactionId, ImageChannel channel, String tenantId);

    /**
     * Finds all transaction IDs within a specified time frame.
     *
     * @param tenantId The ID of the tenant.
     * @param from     The start of the time frame.
     * @param to       The end of the time frame.
     * @return A list of transaction IDs.
     */
    @Query("SELECT DISTINCT(i.transactionId) FROM Image i WHERE i.tenant.tenantId = :tenantId AND i.measuredAt >= :from AND i.measuredAt <= :to")
    List<String> findAllTransactionIdsWithinTimeFrame(String tenantId, Instant from, Instant to);


    /**
     * Finds the first image with the specified transaction ID ordered by the measuredAt in ascending order.
     *
     * @param transactionId The transaction ID to search for.
     * @return The first image with the specified transaction ID ordered by the measuredAt in ascending order.
     */
    Image findFirstByTransactionIdOrderByMeasuredAtAsc(String transactionId);

}
