package de.app.fivegla.integration.weenat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.weenat.model.Measurement;
import de.app.fivegla.integration.weenat.model.MeasurementValues;
import de.app.fivegla.integration.weenat.model.Measurements;
import de.app.fivegla.integration.weenat.model.Plot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.*;

@Slf4j
@Service
public class WeenatMeasuresIntegrationService extends AbstractIntegrationService {

    private final WeenatAccessTokenIntegrationService weenatAccessTokenIntegrationService;
    private final WeenatPlotIntegrationService weenatPlotIntegrationService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public WeenatMeasuresIntegrationService(WeenatAccessTokenIntegrationService weenatAccessTokenIntegrationService,
                                            WeenatPlotIntegrationService weenatPlotIntegrationService,
                                            RestTemplate restTemplate) {
        this.weenatAccessTokenIntegrationService = weenatAccessTokenIntegrationService;
        this.weenatPlotIntegrationService = weenatPlotIntegrationService;
        this.restTemplate = restTemplate;
        objectMapper = new ObjectMapper();
    }

    public Map<Plot, Measurements> fetchAll(Instant start) {
        Map<Plot, Measurements> plotsWithMeasurements = new HashMap<>();
        var plots = weenatPlotIntegrationService.fetchAll();
        plots.forEach(plot -> {
            var measurementsBuilder = Measurements.builder().plot(plot);
            var headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(weenatAccessTokenIntegrationService.fetchAccessToken());
            var httpEntity = new HttpEntity<String>(headers);
            var uri = UriComponentsBuilder.fromHttpUrl(getUrl() + "/v2/access/plots/{plotId}/measures?start={start}&end={end}")
                    .encode()
                    .toUriString();
            var uriVariables = Map.of(
                    "plotId",
                    plot.getId(),
                    "start",
                    start.getEpochSecond(),
                    "end",
                    Instant.now());
            var response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class, uriVariables);
            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("Could not fetch measures from the API. Response code was {}.", response.getStatusCode());
                throw new BusinessException(ErrorMessage.builder()
                        .error(Error.WEENAT_COULD_NOT_FETCH_MEASURES)
                        .message("Could not fetch measures from the API.")
                        .build());
            } else {
                log.info("Successfully fetched measures from the API.");
                var metadataResponse = response.getBody();
                if (null == metadataResponse) {
                    throw new BusinessException(ErrorMessage.builder()
                            .error(Error.WEENAT_COULD_NOT_FETCH_MEASURES)
                            .message("Could not fetch measures from the API. Response was empty.")
                            .build());
                } else {
                    log.info("Successfully fetched measures from the API.");
                    var type = objectMapper.getTypeFactory().constructMapType(HashMap.class, Long.class, MeasurementValues.class);
                    try {
                        //noinspection unchecked
                        var measures = (HashMap<Long, MeasurementValues>) objectMapper.readValue(metadataResponse, type);
                        List<Measurement> measurements = new ArrayList<>();
                        measures.forEach((timestamp, measurementValues) -> {
                            measurements.add(Measurement.builder()
                                    .timestamp(Instant.ofEpochSecond(timestamp))
                                    .measurementValues(measurementValues)
                                    .build());
                        });
                        if (!measures.isEmpty()) {
                            plotsWithMeasurements.put(plot, measurementsBuilder.measurements(measurements).build());
                        }
                    } catch (JsonProcessingException e) {
                        throw new BusinessException(ErrorMessage.builder()
                                .error(Error.WEENAT_COULD_NOT_FETCH_MEASURES)
                                .message("Could not parse measures from the JSON response of the API.")
                                .build());
                    }
                }
            }
        });
        return plotsWithMeasurements;
    }


}