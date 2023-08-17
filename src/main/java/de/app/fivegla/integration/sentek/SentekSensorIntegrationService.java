package de.app.fivegla.integration.sentek;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.sentek.model.User;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.StringReader;

@Slf4j
@Service
public class SentekSensorIntegrationService extends AbstractIntegrationService {

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
