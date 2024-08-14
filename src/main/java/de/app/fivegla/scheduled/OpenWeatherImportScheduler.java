package de.app.fivegla.scheduled;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.business.RegisteredDevicesService;
import de.app.fivegla.business.TenantService;
import de.app.fivegla.business.ThirdPartyApiConfigurationService;
import de.app.fivegla.event.events.OpenWeatherImportEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled import of weather data from OpenWeather.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OpenWeatherImportScheduler {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final TenantService tenantService;
    private final RegisteredDevicesService registeredDevicesService;
    private final ThirdPartyApiConfigurationService thirdPartyApiConfigurationService;

    /**
     * Schedule import of weather data from OpenWeather.
     */
    @Scheduled(initialDelayString = "${app.scheduled.openweather-import.initial-delay}", fixedDelayString = "${app.scheduled.openweather-import.delay}")
    public void scheduleOpenWeatherImport() {
        log.info("Scheduled OpenWeather import started.");
        tenantService.findAll().forEach(tenant -> thirdPartyApiConfigurationService.findByManufacturer(tenant, Manufacturer.OPEN_WEATHER).ifPresent(thirdPartyApiConfiguration -> registeredDevicesService.findAll(tenant).forEach(registeredDevice -> applicationEventPublisher.publishEvent(new OpenWeatherImportEvent(this, registeredDevice.getGroup(), thirdPartyApiConfiguration, registeredDevice.getLongitude(), registeredDevice.getLatitude())))));

    }
}
