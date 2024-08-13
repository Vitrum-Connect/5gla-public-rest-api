package de.app.fivegla.event;

import de.app.fivegla.event.events.OpenWeatherImportEvent;
import de.app.fivegla.integration.openweather.OpenWeatherIntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenWeatherImportEventHandler {

    public final OpenWeatherIntegrationService openWeatherIntegrationService;

    @EventListener(OpenWeatherImportEvent.class)
    public void handleOpenWeatherImportEvent(OpenWeatherImportEvent openWeatherImportEvent) {
        log.info("Handling OpenWeather import event for longitude {} and latitude {}.", openWeatherImportEvent.getLongitude(), openWeatherImportEvent.getLatitude());
        var openWeatherData = openWeatherIntegrationService.fetchWeatherData(openWeatherImportEvent.getThirdPartyApiConfiguration().getApiToken(), openWeatherImportEvent.getLatitude(), openWeatherImportEvent.getLongitude());
        log.info("Successfully imported weather data from OpenWeather for longitude {} and latitude {}.", openWeatherImportEvent.getLongitude(), openWeatherImportEvent.getLatitude());
        log.debug("OpenWeather data: {}", openWeatherData);
    }

}
