package de.app.fivegla.integration.csv;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Test for {@link CsvDataImportIntegrationService}.
 */
class CsvDataImportIntegrationServiceTest {

    private final CsvDataImportIntegrationService csvDataImportIntegrationService = new CsvDataImportIntegrationService();

    @Test
    void givenValidCsvDataForImportWhenReadingHeadersThenTheServiceShouldReturnTheHeaders() throws Throwable {
        var resource = this.getClass().getClassLoader().getResource("csv-data-import-test.csv");
        var fileContent = Files.readString(Path.of(Objects.requireNonNull(resource).toURI()));
        var sensorData = csvDataImportIntegrationService.readSensorDataFromCsv(fileContent);

        Assertions.assertEquals(4706 - 1, sensorData.size()); // -1 because of the header line

        var firstLine = sensorData.get(0);
        Assertions.assertNotNull(firstLine.getTimestamp());
        Assertions.assertEquals(21874, firstLine.getDevice());
        Assertions.assertEquals(0.24, firstLine.getMoisture());
        Assertions.assertEquals(7.5, firstLine.getTemperature());
        Assertions.assertEquals(17.6631, firstLine.getDielectricity());
        Assertions.assertEquals(0.082, firstLine.getSalinity());
        Assertions.assertEquals(0.2, firstLine.getFieldCapacity());
        Assertions.assertEquals(0.0, firstLine.getWiltingPoint());
        Assertions.assertEquals(1.267, firstLine.getWaterBalance());
    }

}