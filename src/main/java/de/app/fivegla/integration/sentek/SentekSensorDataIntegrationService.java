package de.app.fivegla.integration.sentek;

import com.opencsv.bean.CsvToBeanBuilder;
import de.app.fivegla.integration.sentek.model.csv.Reading;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.List;

/**
 * Date Time,V1,V2,A1(5),T1(5),A2(15),T2(15),A3(25),T3(25),A4(35),T4(35),A5(45),T5(45),A6(55),T6(55),A7(65),T7(65),A8(75),T8(75),A9(85),T9(85)
 */
@Slf4j
@Service
public class SentekSensorDataIntegrationService {

    protected List<Reading> parse(String csv) {
        var csvReader = new CsvToBeanBuilder<Reading>(new StringReader(csv))
                .withType(Reading.class)
                .build();
        var readings = csvReader.parse();
        return readings;
    }

}
