package de.app.fivegla.integration.micasense;

import de.app.fivegla.integration.micasense.fiware.MicaSenseFiwareIntegrationServiceWrapper;
import de.app.fivegla.model.micasense.MicaSenseChannel;
import de.app.fivegla.model.micasense.MicaSenseImage;
import de.app.fivegla.persistence.ApplicationDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Service for Mica Sense integration.
 */
@Slf4j
@Service
public class MicaSenseIntegrationService {

    private final ExifDataIntegrationService exifDataIntegrationService;
    private final MicaSenseFiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper;
    private final ApplicationDataRepository applicationDataRepository;

    public MicaSenseIntegrationService(ExifDataIntegrationService exifDataIntegrationService,
                                       MicaSenseFiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper,
                                       ApplicationDataRepository applicationDataRepository) {
        this.exifDataIntegrationService = exifDataIntegrationService;
        this.fiwareIntegrationServiceWrapper = fiwareIntegrationServiceWrapper;
        this.applicationDataRepository = applicationDataRepository;
    }

    /**
     * Processes an image from the mica sense camera.
     *
     * @param micaSenseChannel The channel the image was taken with.
     * @param base64Image      The base64 encoded tiff image.
     */
    public String processImage(String droneId, MicaSenseChannel micaSenseChannel, String base64Image) {
        fiwareIntegrationServiceWrapper.createOrUpdateDevice(droneId);
        var image = Base64.getDecoder().decode(base64Image);
        var location = exifDataIntegrationService.readLocation(image);
        log.debug("Channel for the image: {}.", micaSenseChannel);
        log.debug("Location for the image: {}.", location.getCoordinates());
        var micaSenseImage = applicationDataRepository.addMicaSenseImage(MicaSenseImage.builder()
                .oid(UUID.randomUUID().toString())
                .channel(micaSenseChannel)
                .droneId(droneId)
                .base64Image(base64Image)
                .location(exifDataIntegrationService.readLocation(image))
                .measuredAt(exifDataIntegrationService.readMeasuredAt(image))
                .build());
        log.debug("Image with oid {} added to the application data.", micaSenseImage.getOid());
        fiwareIntegrationServiceWrapper.createDroneDeviceMeasurement(micaSenseImage);
        return micaSenseImage.getOid();
    }

    /**
     * Returns the image with the given oid.
     *
     * @param oid The oid of the image.
     * @return The image with the given oid.
     */
    public Optional<byte[]> getImage(String oid) {
        AtomicReference<Optional<byte[]>> result = new AtomicReference<>(Optional.empty());
        applicationDataRepository.getImage(oid).ifPresent(image -> {
            log.debug("Image with oid {} found.", oid);
            result.set(Optional.of(Base64.getDecoder().decode(image.getBase64Image())));
        });
        return result.get();
    }
}
