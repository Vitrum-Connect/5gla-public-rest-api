package de.app.fivegla.integration.openweather;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.api.enums.EntityType;
import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.business.RegisteredDevicesService;
import de.app.fivegla.business.ThirdPartyApiConfigurationService;
import de.app.fivegla.integration.fiware.FiwareEntityIntegrationService;
import de.app.fivegla.integration.fiware.model.WeatherData;
import de.app.fivegla.integration.fiware.model.internal.DateAttribute;
import de.app.fivegla.integration.fiware.model.internal.NumberAttribute;
import de.app.fivegla.integration.fiware.model.internal.TextAttribute;
import de.app.fivegla.business.RegisteredDevicesService;
import de.app.fivegla.business.ThirdPartyApiConfigurationService;
import de.app.fivegla.integration.fiware.FiwareEntityIntegrationService;
import de.app.fivegla.integration.openweather.dto.OpenWeatherData;
import de.app.fivegla.integration.openweather.dto.OpenWeatherDataFromThePast;
import de.app.fivegla.persistence.entity.Group;
import de.app.fivegla.persistence.entity.Tenant;
import de.app.fivegla.persistence.entity.Group;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenWeatherIntegrationService {

    private static final String OPENWEATHERMAP_API_URL = "https://api.openweathermap.org/data/3.0/onecall?units=metric&exclude=minutely,hourly,daily,alerts";
    private static final String OPENWEATHERMAP_API_TIMEMACHINE_URL = "https://api.openweathermap.org/data/3.0/onecall/timemachine?units=metric";

    private final RestTemplate restTemplate;
    private final RegisteredDevicesService registeredDevicesService;
    private final ThirdPartyApiConfigurationService thirdPartyApiConfigurationService;
    private final FiwareEntityIntegrationService fiwareEntityIntegrationService;

    /**
     * Fetches weather data from the OpenWeather API for the given longitude and latitude.
     *
     * @param apiToken  The API token to use for the request.
     * @param latitude  The latitude for which to fetch the weather data.
     * @param longitude The longitude for which to fetch the weather data.
     * @return The weather data fetched from the OpenWeather API.
     */
    public OpenWeatherData fetchWeatherData(String apiToken, double latitude, double longitude) {
        try {
            log.info("Importing weather data from OpenWeather for longitude {} and latitude {}.", longitude, latitude);
            var concattedApiUrl = OPENWEATHERMAP_API_URL
                    .concat("&lat=").concat(String.valueOf(latitude))
                    .concat("&lon=").concat(String.valueOf(longitude))
                    .concat("&appid=").concat(apiToken);
            var openWeatherData = restTemplate.getForObject(concattedApiUrl, OpenWeatherData.class);
            if (openWeatherData == null) {
                log.error("Failed to import weather data from OpenWeather for longitude {} and latitude {}.", longitude, latitude);
                throw new BusinessException(ErrorMessage.builder()
                        .error(Error.COULD_NOT_IMPORT_DATA_FROM_OPEN_WEATHER).
                        message("Failed to import weather data from OpenWeather for longitude " + longitude + " and latitude " + latitude + ".")
                        .build());
            } else {
                log.info("Successfully imported weather data from OpenWeather for longitude {} and latitude {}.", longitude, latitude);
                return openWeatherData;
            }
        } catch (Exception e) {
            log.error("Failed to import weather data from OpenWeather for longitude {} and latitude {}.", longitude, latitude, e);
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.COULD_NOT_IMPORT_DATA_FROM_OPEN_WEATHER).
                    message("Failed to import weather data from OpenWeather for longitude " + longitude + " and latitude " + latitude + ".")
                    .build());
        }
    }

    /**
     * Imports weather data from the past using the provided tenant, sensor ID, and start date.
     *
     * @param tenant             the tenant associated with the weather data
     * @param sensorId           the ID of the sensor for which to import weather data
     * @param startDateInThePast the start date from which to import weather data
     */
    @Async
    public void importWeatherDataFromThePast(Tenant tenant, String sensorId, LocalDate startDateInThePast) {
        thirdPartyApiConfigurationService.findByManufacturer(tenant, Manufacturer.OPEN_WEATHER).ifPresent(thirdPartyApiConfiguration -> {
            var apiToken = thirdPartyApiConfiguration.getApiToken();
            registeredDevicesService.findByTenantAndSensorId(tenant, sensorId).ifPresent(registeredDevice -> {
                var longitude = registeredDevice.getLongitude();
                var latitude = registeredDevice.getLatitude();
                fetchWeatherData(apiToken, latitude, longitude, registeredDevice.getGroup(), registeredDevice.getTenant(), startDateInThePast);
                log.info("Imported weather data from OpenWeather for sensor '{}'.", sensorId);
            });
        });
    }

    private void fetchWeatherData(String apiToken, double latitude, double longitude, Group group, Tenant tenant, LocalDate startDateInThePast) {
        try {
            log.info("Iterating form the start date in the past, which is: {}", startDateInThePast);
            var currentIterationDate = startDateInThePast;
            while (currentIterationDate.isBefore(LocalDate.now())) {
                log.info("Fetching weather data from OpenWeather for longitude {}, latitude {} and timestamp {}.", longitude, latitude, currentIterationDate);
                var openWeatherDataFromThePast = fetchWeatherDataFromThePast(apiToken, latitude, longitude, currentIterationDate);
                if (openWeatherDataFromThePast.isEmpty()) {
                    break;
                } else {
                    OpenWeatherDataFromThePast historicalWeatherData = openWeatherDataFromThePast.get();
                    var weatherData = new WeatherData(
                            UUID.randomUUID().toString(),
                            EntityType.OPEN_WEATHER_MAP.getKey(),
                            new TextAttribute(group.getOid()),
                            new DateAttribute(Date.from(Instant.ofEpochSecond(historicalWeatherData.getData().getTimestamp()))),
                            historicalWeatherData.getLatitude(),
                            historicalWeatherData.getLongitude(),
                            new NumberAttribute(historicalWeatherData.getData().getTemp()),
                            new NumberAttribute(historicalWeatherData.getData().getPressure()),
                            new NumberAttribute(historicalWeatherData.getData().getHumidity()),
                            new NumberAttribute(historicalWeatherData.getData().getDewPoint()),
                            new NumberAttribute(historicalWeatherData.getData().getUvi()),
                            new NumberAttribute(historicalWeatherData.getData().getClouds()),
                            new NumberAttribute(historicalWeatherData.getData().getVisibility()),
                            new NumberAttribute(historicalWeatherData.getData().getWindSpeed()),
                            new NumberAttribute(historicalWeatherData.getData().getWindDeg()),
                            new NumberAttribute(historicalWeatherData.getData().getWindGust()),
                            new NumberAttribute(historicalWeatherData.getData().getRain() != null ? historicalWeatherData.getData().getRain().getOneHour() : 0),
                            new NumberAttribute(historicalWeatherData.getData().getSnow() != null ? historicalWeatherData.getData().getSnow().getOneHour() : 0)
                    );
                    log.info("Persisting weather data from OpenWeather to FIWARE for longitude {}, latitude {} and timestamp {}.", longitude, latitude, currentIterationDate);
                    fiwareEntityIntegrationService.persist(tenant, group, weatherData);
                    currentIterationDate = currentIterationDate.plusDays(1);
                }
            }
        } catch (Exception e) {
            log.error("Failed to import weather data from OpenWeather for longitude {} and latitude {}.", longitude, latitude, e);
        }
    }

    private Optional<OpenWeatherDataFromThePast> fetchWeatherDataFromThePast(String apiToken, double latitude, double longitude, LocalDate startDateInThePast) {
        try {
            var concattedApiUrl = OPENWEATHERMAP_API_TIMEMACHINE_URL
                    .concat("&lat=").concat(String.valueOf(latitude))
                    .concat("&lon=").concat(String.valueOf(longitude))
                    .concat("&dt=").concat(String.valueOf(startDateInThePast.atStartOfDay(ZoneId.systemDefault()).toEpochSecond()))
                    .concat("&appid=").concat(apiToken);
            var openWeatherDataFromThePast = restTemplate.getForObject(concattedApiUrl, OpenWeatherDataFromThePast.class);
            if (openWeatherDataFromThePast == null) {
                log.error("Failed to import weather data from OpenWeather for longitude {}, latitude {} and timestamp {}.", longitude, latitude, startDateInThePast);
                return Optional.empty();
            } else {
                return Optional.of(openWeatherDataFromThePast);
            }
        } catch (Exception e) {
            log.error("Failed to import weather data from OpenWeather for longitude {} and latitude {}.", longitude, latitude, e);
            return Optional.empty();
        }
    }
}
