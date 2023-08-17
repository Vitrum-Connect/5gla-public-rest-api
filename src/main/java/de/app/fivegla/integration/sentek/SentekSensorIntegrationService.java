package de.app.fivegla.integration.sentek;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.sentek.model.xml.Logger;
import de.app.fivegla.integration.sentek.model.xml.User;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SentekSensorIntegrationService extends AbstractIntegrationService {


    /**
     * Fetches all logger data from the Sentek API.
     *
     * @return A list of Logger objects representing the fetched data.
     * @throws BusinessException If there was an error fetching the data from the Sentek API.
     */
    public List<Logger> fetchAll() {
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));
        var httpEntity = new HttpEntity<String>(headers);
        var uri = UriComponentsBuilder.fromHttpUrl(url + "/?cmd=getloggers&key={apiToken}")
                .encode()
                .toUriString();
        log.debug("Fetching sensor data from URI: {}", uri);
        var uriVariables = Map.of("apiToken", getApiToken());
        var response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class, uriVariables);
        if (response.getStatusCode().is2xxSuccessful()) {
            return parse(response.getBody()).getLoggers();
        } else {
            var errorMessage = ErrorMessage.builder()
                    .error(Error.SENTEK_COULD_NOT_FETCH_SENSORS)
                    .message("Could not fetch sensors from Sentek API.")
                    .build();
            throw new BusinessException(errorMessage);
        }
    }

    /**
     * Parses the given XML string and returns a User object.
     *
     * @param xml The XML string to be parsed.
     * @return The User object parsed from the XML string.
     * @throws BusinessException If there is an error during XML parsing.
     */
    protected User parse(String xml) {
        log.info("Parsing xml: {}", xml);
        try {
            var jaxbContext = JAXBContext.newInstance(User.class);
            var unmarshaller = jaxbContext.createUnmarshaller();
            var unmarshalled = unmarshaller.unmarshal(new StringReader(xml));
            return (User) unmarshalled;
        } catch (JAXBException e) {
            log.error("Could not parse xml: {}", xml, e);
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.SENTEK_XML_PARSING_ERROR)
                    .message("Could not parse sensor data.")
                    .build());
        }
    }

}
