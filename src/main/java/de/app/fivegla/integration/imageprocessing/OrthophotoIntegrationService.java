package de.app.fivegla.integration.imageprocessing;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.imageprocessing.dto.TriggerOrthophotoProcessingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrthophotoIntegrationService {

    private final RestTemplate restTemplate;

    @Value("${app.orthophoto.api-url}")
    private String orthophotoProcessingApi;

    @Value("${app.orthophoto.api-key}")
    private String orthophotoProcessingApiKey;

    /**
     * Triggers the orthophoto processing for the given transaction id.
     *
     * @param transactionId The transaction id.
     * @return The UUID of the triggered orthophoto processing.
     */
    public String triggerOrthophotoProcessing(String transactionId) {
        log.info("Triggering orthophoto processing for transaction id: {}", transactionId);
        try {
            var headers = new HttpHeaders();
            headers.set("X-API-KEY", orthophotoProcessingApiKey);
            var httpEntity = new HttpEntity<>(headers);
            var uri = UriComponentsBuilder.fromHttpUrl(orthophotoProcessingApi + "/calculate_orthophoto/" + transactionId)
                    .toUriString();
            var response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, TriggerOrthophotoProcessingResponse.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("Error while starting the image calculation using the API. Status code: {}", response.getStatusCode());
                throw new BusinessException(ErrorMessage.builder()
                        .error(Error.ORTHOPHOTO_COULD_NOT_TRIGGER_CALCULATION)
                        .message("Could not trigger the orthophoto calculation.")
                        .build());
            } else {
                var triggerOrthophotoProcessingResponse = response.getBody();
                if (null != triggerOrthophotoProcessingResponse.uuid()) {
                    log.info("Successfully triggered orthophoto processing for transaction id: {}.", transactionId);
                    log.info("Orthophoto being calculated using the following UUID: {}", triggerOrthophotoProcessingResponse.uuid());
                    return triggerOrthophotoProcessingResponse.uuid();
                } else {
                    log.error("Error while starting the image calculation using the API. Status code: {}", response.getStatusCode());
                    throw new BusinessException(ErrorMessage.builder()
                            .error(Error.ORTHOPHOTO_COULD_NOT_TRIGGER_CALCULATION)
                            .message("Could not trigger the orthophoto calculation, the UUID in the response is null.")
                            .build());
                }
            }
        } catch (Exception e) {
            log.error("Error while starting the image calculation using the API.", e);
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.ORTHOPHOTO_COULD_NOT_TRIGGER_CALCULATION)
                    .message("Could not trigger the orthophoto calculation.")
                    .build());
        }
    }
}
