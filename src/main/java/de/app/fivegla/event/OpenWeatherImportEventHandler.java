package de.app.fivegla.event;

import de.app.fivegla.api.enums.EntityType;
import de.app.fivegla.event.events.OpenWeatherImportEvent;
import de.app.fivegla.integration.fiware.FiwareEntityIntegrationService;
import de.app.fivegla.integration.fiware.model.WeatherData;
import de.app.fivegla.integration.fiware.model.internal.DateAttribute;
import de.app.fivegla.integration.fiware.model.internal.NumberAttribute;
import de.app.fivegla.integration.fiware.model.internal.TextAttribute;
import de.app.fivegla.integration.openweather.OpenWeatherIntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenWeatherImportEventHandler {

    public final OpenWeatherIntegrationService openWeatherIntegrationService;
    public final FiwareEntityIntegrationService fiwareEntityIntegrationService;

    @EventListener(OpenWeatherImportEvent.class)
    public void handleOpenWeatherImportEvent(OpenWeatherImportEvent openWeatherImportEvent) {
        log.info("Handling OpenWeather import event for longitude {} and latitude {}.", openWeatherImportEvent.getLongitude(), openWeatherImportEvent.getLatitude());
        var openWeatherData = openWeatherIntegrationService.fetchWeatherData(openWeatherImportEvent.getThirdPartyApiConfiguration().getApiToken(), openWeatherImportEvent.getLatitude(), openWeatherImportEvent.getLongitude());
        log.info("Successfully imported weather data from OpenWeather for longitude {} and latitude {}.", openWeatherImportEvent.getLongitude(), openWeatherImportEvent.getLatitude());
        log.debug("OpenWeather data: {}", openWeatherData);
        var weatherData = new WeatherData(
                UUID.randomUUID().toString(),
                EntityType.OPEN_WEATHER_MAP.getKey(),
                new TextAttribute(openWeatherImportEvent.getGroup().getOid()),
                new DateAttribute(Date.from(Instant.ofEpochSecond(openWeatherData.getCurrent().getDt()))),
                openWeatherData.getLatitude(),
                openWeatherData.getLongitude(),
                new NumberAttribute(openWeatherData.getCurrent().getTemp()),
                new NumberAttribute(openWeatherData.getCurrent().getPressure()),
                new NumberAttribute(openWeatherData.getCurrent().getHumidity()),
                new NumberAttribute(openWeatherData.getCurrent().getDewPoint()),
                new NumberAttribute(openWeatherData.getCurrent().getUvi()),
                new NumberAttribute(openWeatherData.getCurrent().getClouds()),
                new NumberAttribute(openWeatherData.getCurrent().getVisibility()),
                new NumberAttribute(openWeatherData.getCurrent().getWindSpeed()),
                new NumberAttribute(openWeatherData.getCurrent().getWindDeg()),
                new NumberAttribute(openWeatherData.getCurrent().getWindGust()),
                new NumberAttribute(openWeatherData.getCurrent().getRain() != null ? openWeatherData.getCurrent().getRain().getOneHour() : 0),
                new NumberAttribute(openWeatherData.getCurrent().getSnow() != null ? openWeatherData.getCurrent().getSnow().getOneHour() : 0)
        );
        fiwareEntityIntegrationService.persist(openWeatherImportEvent.getThirdPartyApiConfiguration().getTenant(), openWeatherImportEvent.getGroup(), weatherData);
    }

}
