package de.app.fivegla.event.events;

import de.app.fivegla.persistence.entity.ThirdPartyApiConfiguration;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event for importing weather data from OpenWeather.
 */
@Getter
public class OpenWeatherImportEvent extends ApplicationEvent {

    private final ThirdPartyApiConfiguration thirdPartyApiConfiguration;
    private final double longitude;
    private final double latitude;

    public OpenWeatherImportEvent(Object source, ThirdPartyApiConfiguration thirdPartyApiConfiguration, double longitude, double latitude) {
        super(source);
        this.thirdPartyApiConfiguration = thirdPartyApiConfiguration;
        this.longitude = longitude;
        this.latitude = latitude;

    }
}
