package de.app.fivegla.persistence;

import de.app.fivegla.api.dto.SortableImageOids;
import de.app.fivegla.persistence.entity.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ImageRepository {

    private final ApplicationData applicationData;

    /**
     * Returns the image with the given oid.
     *
     * @param oid The oid of the image.
     * @return The image with the given oid.
     */
    public Optional<Image> getImage(String oid) {
        return applicationData.getImages().stream()
                .filter(image -> image.getOid().equals(oid))
                .findFirst();
    }

    /**
     * Add image to the list of images.
     *
     * @param image The image to add.
     * @return The added image.
     */
    public Image addImage(Image image) {
        return applicationData.addImage(image);
    }

    /**
     * Retrieves the image Object IDs (Oids) associated with a specific transaction.
     *
     * @param transactionId The ID of the transaction.
     * @return A list of image Object IDs (Oids) associated with the specified transaction.
     */
    public List<SortableImageOids> getImageOidsForTransaction(String transactionId) {
        return applicationData.getImages().stream()
                .filter(image -> image.getTransactionId().equals(transactionId))
                .map(image -> new SortableImageOids(image.getOid(), image.getMeasuredAt()))
                .toList();
    }
}
