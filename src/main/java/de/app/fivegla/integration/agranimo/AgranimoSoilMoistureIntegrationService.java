package de.app.fivegla.integration.agranimo;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.agranimo.model.SoilMoisture;
import de.app.fivegla.integration.agranimo.model.SoilMoistureType;
import de.app.fivegla.integration.agranimo.model.Zone;
import de.app.fivegla.persistence.entity.ThirdPartyApiConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Service for integration with Agranimo.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AgranimoSoilMoistureIntegrationService {

    private final AgranimoLoginIntegrationService loginService;
    private final RestTemplate restTemplate;

    /**
     * Fetch the water content from the API.
     *
     * @param thirdPartyApiConfiguration The configuration for the third party API.
     * @param zone                       The group to fetch the data for.
     * @param since                      The date since to fetch the data.
     * @return The water content.
     */
    public List<SoilMoisture> fetchWaterContent(ThirdPartyApiConfiguration thirdPartyApiConfiguration, Zone zone, Instant since) {
        return fetchAll(thirdPartyApiConfiguration, zone, since);
    }

    /**
     * Fetch the soil moisture from the API.
     */
    private List<SoilMoisture> fetchAll(ThirdPartyApiConfiguration thirdPartyApiConfiguration, Zone zone, Instant since) {
        var until = Instant.now();
        log.info("Fetching soil moisture data for group {}.", zone.getName());
        log.debug("Fetching soil moisture data for group {} from {} to {}.", zone.getId(), since, until);
        try {
            var headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(loginService.fetchAccessToken(thirdPartyApiConfiguration));
            var httpEntity = new HttpEntity<>(headers);
            var uri = UriComponentsBuilder.fromHttpUrl(thirdPartyApiConfiguration.getUrl() + "/group/{zoneId}/data/soilmoisture?dateStart={since}&dateEnd={until}&type={type}")
                    .encode()
                    .toUriString();
            var uriVariables = Map.of(
                    "zoneId",
                    zone.getId(),
                    "since",
                    since.getEpochSecond(),
                    "until",
                    until.getEpochSecond(),
                    "type",
                    SoilMoistureType.WATER_CONTENT.getKey());
            log.debug("Calling API with URI {} and variables {}.", uri, uriVariables);
            var response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, SoilMoisture[].class, uriVariables);

            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("Error while fetching soil moisture from the API. Status code: {}", response.getStatusCode());
                log.info("Could not fetch soil moisture data for group {}.", zone.getName());
                throw new BusinessException(ErrorMessage.builder()
                        .error(Error.AGRANIMO_COULD_NOT_FETCH_SOIL_MOISTURE_FOR_ZONE)
                        .message("Could not fetch soil moisture.")
                        .build());
            } else {
                log.info("Successfully fetched soil moisture from the API.");
                log.info("Successfully fetched soil moisture data for group {}.", zone.getName());
                var soilMoistures = response.getBody();
                if (null != soilMoistures) {
                    log.info("Successfully fetched {} soil moisture data points for group {}.", soilMoistures.length, zone.getName());
                    return List.of(soilMoistures);
                } else {
                    log.info("Could not fetch soil moisture data for group {}.", zone.getName());
                    throw new BusinessException(ErrorMessage.builder()
                            .error(Error.AGRANIMO_COULD_NOT_FETCH_SOIL_MOISTURE_FOR_ZONE)
                            .message("Could not fetch soil moisture.")
                            .build());
                }
            }
        } catch (Exception e) {
            log.error("Error while fetching soil moisture from the API.", e);
            log.info("Could not fetch soil moisture data for group {}.", zone.getName());
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.AGRANIMO_COULD_NOT_FETCH_SOIL_MOISTURE)
                    .message("Could not fetch soil moisture.")
                    .build());
        }
    }

}
