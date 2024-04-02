package de.app.fivegla.integration.agranimo;


import de.app.fivegla.api.enums.MeasurementType;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.internal.DateTimeAttribute;
import de.app.fivegla.fiware.model.internal.EmptyAttribute;
import de.app.fivegla.fiware.model.internal.NumberAttribute;
import de.app.fivegla.fiware.model.internal.TextAttribute;
import de.app.fivegla.integration.agranimo.model.SoilMoisture;
import de.app.fivegla.integration.agranimo.model.Zone;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class AgranimoFiwareIntegrationServiceWrapper {
    private final DeviceMeasurementIntegrationService deviceMeasurementIntegrationService;

    /**
     * Persists the soil moisture measurement for a given zone.
     *
     * @param zone         the zone associated with the soil moisture measurement
     * @param soilMoisture the soil moisture measurement to persist
     */
    public void persist(Tenant tenant, Zone zone, SoilMoisture soilMoisture) {
        var smo1 = new DeviceMeasurement(
                tenant.getFiwarePrefix() + soilMoisture.getDeviceId(),
                MeasurementType.AGRANIMO_SENSOR.name(),
                new TextAttribute("smo1"),
                new NumberAttribute(soilMoisture.getSmo1()),
                new DateTimeAttribute(soilMoisture.getTms()),
                new EmptyAttribute(),
                zone.getData().getPoint().getCoordinates()[0],
                zone.getData().getPoint().getCoordinates()[1]);
        deviceMeasurementIntegrationService.persist(smo1);

        var smo2 = new DeviceMeasurement(
                tenant.getFiwarePrefix() + soilMoisture.getDeviceId(),
                MeasurementType.AGRANIMO_SENSOR.name(),
                new TextAttribute("smo2"),
                new NumberAttribute(soilMoisture.getSmo2()),
                new DateTimeAttribute(soilMoisture.getTms()),
                new EmptyAttribute(),
                zone.getData().getPoint().getCoordinates()[0],
                zone.getData().getPoint().getCoordinates()[1]);
        deviceMeasurementIntegrationService.persist(smo2);

        var smo3 = new DeviceMeasurement(
                tenant.getFiwarePrefix() + soilMoisture.getDeviceId(),
                MeasurementType.AGRANIMO_SENSOR.name(),
                new TextAttribute("smo3"),
                new NumberAttribute(soilMoisture.getSmo3()),
                new DateTimeAttribute(soilMoisture.getTms()),
                new EmptyAttribute(),
                zone.getData().getPoint().getCoordinates()[0],
                zone.getData().getPoint().getCoordinates()[1]);
        deviceMeasurementIntegrationService.persist(smo3);

        var smo4 = new DeviceMeasurement(
                tenant.getFiwarePrefix() + soilMoisture.getDeviceId(),
                MeasurementType.AGRANIMO_SENSOR.name(),
                new TextAttribute("smo4"),
                new NumberAttribute(soilMoisture.getSmo4()),
                new DateTimeAttribute(soilMoisture.getTms()),
                new EmptyAttribute(),
                zone.getData().getPoint().getCoordinates()[0],
                zone.getData().getPoint().getCoordinates()[1]);
        deviceMeasurementIntegrationService.persist(smo4);
    }

}
