package de.app.fivegla.integration.micasense.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * An event that is triggered when image processing has started for a drone.
 */
@Getter
public class ImageProcessingStartedEvent extends ApplicationEvent {

    /**
     * The drone id.
     */
    private final String droneId;

    /**
     * The transaction id.
     */
    private final String transactionId;


    /**
     * Constructor for the ImageProcessingStartedEvent class.
     *
     * @param source        The object that triggered the event.
     * @param droneId       The drone ID for which image processing has started.
     * @param transactionId The transaction ID associated with the image processing.
     */
    public ImageProcessingStartedEvent(Object source, String droneId, String transactionId) {
        super(source);
        this.droneId = droneId;
        this.transactionId = transactionId;
    }
}
