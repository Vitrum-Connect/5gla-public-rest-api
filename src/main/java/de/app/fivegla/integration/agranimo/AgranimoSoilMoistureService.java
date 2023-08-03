package de.app.fivegla.integration.agranimo;

import de.app.fivegla.integration.agranimo.domain.SoilMoistureType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;

/**
 * Service for integration with Agranimo.
 */
@Slf4j
@Service
public class AgranimoSoilMoistureService {

    @Value("${app.sensors.agranimo.url}")
    private String url;

    private final ZoneService zoneService;
    private final LoginService loginService;

    public AgranimoSoilMoistureService(ZoneService zoneService, LoginService loginService) {
        this.zoneService = zoneService;
        this.loginService = loginService;
    }

    /**
     * Fetch the water content from the API.
     *
     * @param since The date since to fetch the data.
     * @param until The date until to fetch the data.
     */
    public void fetchWaterContent(Instant since, Instant until) {
        fetchData(since, until, SoilMoistureType.WATER_CONTENT);
    }

    /**
     * Fetch the water height from the API.
     *
     * @param since The date since to fetch the data.
     * @param until The date until to fetch the data.
     */
    public void fetchWaterHeight(Instant since, Instant until) {
        fetchData(since, until, SoilMoistureType.WATER_HEIGHT);
    }

    /**
     * Fetch the water volume from the API.
     *
     * @param since The date since to fetch the data.
     * @param until The date until to fetch the data.
     */
    public void fetchWaterVolume(Instant since, Instant until) {
        fetchData(since, until, SoilMoistureType.WATER_VOLUMETRIC);
    }

    /**
     * Fetch the soil moisture from the API.
     */
    private void fetchData(Instant since, Instant until, SoilMoistureType soilMoistureType) {
        zoneService.fetchZones().forEach(zone -> {
            log.info("Fetching soil moisture data for zone {}.", zone.getName());
            log.debug("Fetching soil moisture data for zone {} from {} to {}.", zone.getId(), since, until);
            try {
                var headers = new HttpHeaders();
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                headers.setBearerAuth(loginService.fetchAccessToken());
                var httpEntity = new HttpEntity<>(headers);
                var uri = UriComponentsBuilder.fromHttpUrl(url + "/zone/{zoneId}/data/soilmoisture?dateStart={since}&dateEnd={until}&type={type}")
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
                        soilMoistureType.getKey());
                log.debug("Calling API with URI {} and variables {}.", uri, uriVariables);
                var response = new RestTemplate().exchange(uri, HttpMethod.GET, httpEntity, String.class, uriVariables);

                if (response.getStatusCode() != HttpStatus.OK) {
                    log.error("Error while fetching soil moisture from the API. Status code: {}", response.getStatusCode());
                    log.info("Could not fetch soil moisture data for zone {}.", zone.getName());
                } else {
                    log.info("Successfully fetched soil moisture from the API.");
                    log.debug("Response body: {}", response.getBody());
                    log.info("Successfully fetched soil moisture data for zone {}.", zone.getName());
                }
            } catch (Exception e) {
                log.error("Error while fetching soil moisture from the API.", e);
                log.info("Could not fetch soil moisture data for zone {}.", zone.getName());
            }
        });
    }

}
