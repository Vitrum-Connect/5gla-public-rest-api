package de.app.fivegla.integration.micasense.events;

import org.springframework.context.ApplicationEvent;

/**
 * Represents an event that is triggered when the image processing is finished.
 */
public class ImageProcessingFinishedEvent extends ApplicationEvent {

    /**
     * The transaction id.
     */
    private final String transactionId;

    public ImageProcessingFinishedEvent(Object source, String transactionId) {
        super(source);
        this.transactionId = transactionId;
    }
}
