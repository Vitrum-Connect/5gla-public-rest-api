package de.app.fivegla.integration.micasense.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Represents an event triggered when the image processing is finished.
 */
@Getter
public class ImageProcessingFinishedEvent extends ApplicationEvent {

    /**
     * The drone id.
     */
    private final String droneId;

    /**
     * The transaction id.
     */
    private final String transactionId;

    public ImageProcessingFinishedEvent(Object source, String droneId, String transactionId) {
        super(source);
        this.droneId = droneId;
        this.transactionId = transactionId;
    }
}
