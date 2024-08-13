package de.app.fivegla.integration.openweather;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.openweather.dto.OpenWeatherData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenWeatherIntegrationService {

    private static final String OPENWEATHERMAP_API_URL = "https://api.openweathermap.org/data/3.0/onecall?&exclude=minutely,hourly,daily,alerts&";
    private final RestTemplate restTemplate;

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
            var concattedApiUrl = OPENWEATHERMAP_API_URL.concat("lat=").concat(String.valueOf(latitude))
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
}
