package de.app.fivegla.persistence;

import de.app.fivegla.persistence.entity.Image;
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
}
