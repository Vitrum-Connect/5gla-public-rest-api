package de.app.fivegla.integration.openweather;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.business.RegisteredDevicesService;
import de.app.fivegla.business.ThirdPartyApiConfigurationService;
import de.app.fivegla.integration.openweather.dto.OpenWeatherData;
import de.app.fivegla.integration.openweather.dto.OpenWeatherDataFromThePast;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenWeatherIntegrationService {

    private static final String OPENWEATHERMAP_API_URL = "https://api.openweathermap.org/data/3.0/onecall?units=metric&exclude=minutely,hourly,daily,alerts";
    private static final String OPENWEATHERMAP_API_TIMEMACHINE_URL = "https://api.openweathermap.org/data/3.0/onecall/timemachine?units=metric";

    private final RestTemplate restTemplate;
    private final RegisteredDevicesService registeredDevicesService;
    private final ThirdPartyApiConfigurationService thirdPartyApiConfigurationService;

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
                var openWeatherData = fetchWeatherData(apiToken, latitude, longitude, startDateInThePast);
                log.info("Imported weather data from OpenWeather for sensor '{}'.", sensorId);
                log.debug("Imported weather data from OpenWeather for sensor '{}': {}", sensorId, openWeatherData);
            });
        });
    }

    private List<OpenWeatherDataFromThePast> fetchWeatherData(String apiToken, double latitude, double longitude, LocalDate startDateInThePast) {
        try {
            var weatherDataFromThePast = new ArrayList<OpenWeatherDataFromThePast>();
            log.info("Iterating form the start date in the past, which is: {}", startDateInThePast);
            var currentIterationDate = startDateInThePast;
            while (currentIterationDate.isBefore(LocalDate.now())) {
                log.info("Fetching weather data from OpenWeather for longitude {}, latitude {} and timestamp {}.", longitude, latitude, currentIterationDate);
                var openWeatherData = fetchWeatherDataFromThePast(apiToken, latitude, longitude, currentIterationDate);
                if (openWeatherData.isEmpty()) {
                    break;
                } else {
                    weatherDataFromThePast.add(openWeatherData.get());
                    currentIterationDate = currentIterationDate.plusDays(1);
                }
            }
            return weatherDataFromThePast;
        } catch (Exception e) {
            log.error("Failed to import weather data from OpenWeather for longitude {} and latitude {}.", longitude, latitude, e);
            return new ArrayList<>();
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
