package de.app.fivegla.persistence;

import de.app.fivegla.persistence.entity.Group;
import de.app.fivegla.persistence.entity.Image;
import de.app.fivegla.persistence.entity.enums.ImageChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
     * @return The images with the given transaction id and channel.
     */
    List<Image> findByTransactionIdAndChannel(String transactionId, ImageChannel channel);
}
