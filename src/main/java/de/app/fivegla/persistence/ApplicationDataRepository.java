package de.app.fivegla.persistence;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.integration.micasense.model.MicaSenseImage;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ApplicationDataRepository {

    private final ApplicationData applicationData;

    public ApplicationDataRepository(ApplicationData applicationData) {
        this.applicationData = applicationData;
    }

    /**
     * Gets last runs.
     *
     * @return the last runs
     */
    public Map<Manufacturer, Instant> getLastRuns() {
        return applicationData.getLastRuns();
    }

    /**
     * Get last run.
     *
     * @param manufacturer the manufacturer
     * @return the last run
     */
    public Optional<Instant> getLastRun(Manufacturer manufacturer) {
        return applicationData != null ? applicationData.getLastRun(manufacturer) : Optional.empty();
    }

    /**
     * Update last run.
     *
     * @param manufacturer the manufacturer
     */
    public void updateLastRun(Manufacturer manufacturer) {
        applicationData.setLastRun(manufacturer, Instant.now());
    }

    /**
     * Returns the image with the given oid.
     *
     * @param oid The oid of the image.
     * @return The image with the given oid.
     */
    public Optional<MicaSenseImage> getImage(String oid) {
        return applicationData.getMicaSenseImages().stream()
                .filter(image -> image.getOid().equals(oid))
                .findFirst();
    }

    /**
     * Add image to the list of images.
     *
     * @param micaSenseImage The image to add.
     * @return The added image.
     */
    public MicaSenseImage addMicaSenseImage(MicaSenseImage micaSenseImage) {
        return applicationData.addMicaSenseImage(micaSenseImage);
    }

    /**
     * Retrieves the image Object IDs (Oids) associated with a specific transaction.
     *
     * @param transactionId The ID of the transaction.
     * @return A list of image Object IDs (Oids) associated with the specified transaction.
     */
    public List<String> getImageOidsForTransaction(String transactionId) {
        return applicationData.getMicaSenseImages().stream()
                .map(MicaSenseImage::getTransactionId)
                .filter(id -> id.equals(transactionId))
                .toList();
    }
}