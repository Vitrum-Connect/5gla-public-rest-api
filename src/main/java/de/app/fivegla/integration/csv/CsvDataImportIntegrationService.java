package de.app.fivegla.integration.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.soilscout.model.SensorData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.List;

/**
 * Service for CSV data import.
 */
@Slf4j
@Service
public class CsvDataImportIntegrationService {

    /**
     * Import CSV data for one or multiple sensors.
     */
    public List<SensorData> readSensorDataFromCsv(String csvData) {
        try {
            var sensorData = new CsvToBeanBuilder<SensorData>(new StringReader(csvData))
                    .withType(SensorData.class)
                    .withSkipLines(1)
                    .build()
                    .parse();
            log.info("CSV data import successful. {} lines read.", sensorData.size());
            return sensorData;
        } catch (Exception e) {
            log.error("Could not read CSV data from the given input.", e);
            throw new BusinessException(ErrorMessage.builder().error(Error.CSV_DATA_IMPORT_FAILED).message("Could not read CSV data from the given input.").build());
        }
    }

}
