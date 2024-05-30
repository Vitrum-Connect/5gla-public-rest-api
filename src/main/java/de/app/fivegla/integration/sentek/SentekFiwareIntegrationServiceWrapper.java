package de.app.fivegla.integration.sentek;


import de.app.fivegla.api.enums.EntityType;
import de.app.fivegla.business.GroupService;
import de.app.fivegla.integration.fiware.FiwareEntityIntegrationService;
import de.app.fivegla.integration.fiware.model.DeviceMeasurement;
import de.app.fivegla.integration.fiware.model.internal.InstantAttribute;
import de.app.fivegla.integration.fiware.model.internal.EmptyAttribute;
import de.app.fivegla.integration.fiware.model.internal.NumberAttribute;
import de.app.fivegla.integration.fiware.model.internal.TextAttribute;
import de.app.fivegla.integration.sentek.model.csv.Reading;
import de.app.fivegla.integration.sentek.model.xml.Logger;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SentekFiwareIntegrationServiceWrapper {
    private final FiwareEntityIntegrationService fiwareEntityIntegrationService;
    private final GroupService groupService;

    public void persist(Tenant tenant, Logger logger, List<Reading> readings) {
        var latitude = logger.getLatitude();
        var longitude = logger.getLongitude();
        readings.forEach(reading -> {
            var group = groupService.findGroupByTenantAndSensorId(tenant, logger.getLoggerId());
            if (group.isDefaultGroupForTenant()) {
                log.warn("Looks like the group for the sensor with id {} is not set. We are using the default group for the tenant.", logger.getLoggerId());
            }
            fiwareEntityIntegrationService.persist(
                    tenant,
                    group,
                    new DeviceMeasurement(
                            tenant.getFiwarePrefix() + logger.getLoggerId(),
                            EntityType.SENTEK_SENSOR.getKey(),
                            new TextAttribute(group.getOid()),
                            new TextAttribute("V1"),
                            new NumberAttribute(reading.getV1()),
                            new InstantAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            fiwareEntityIntegrationService.persist(
                    tenant,
                    group,
                    new DeviceMeasurement(
                            tenant.getFiwarePrefix() + logger.getLoggerId(),
                            EntityType.SENTEK_SENSOR.getKey(),
                            new TextAttribute(group.getOid()),
                            new TextAttribute("V2"),
                            new NumberAttribute(reading.getV2()),
                            new InstantAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            fiwareEntityIntegrationService.persist(
                    tenant,
                    group,
                    new DeviceMeasurement(
                            tenant.getFiwarePrefix() + logger.getLoggerId(),
                            EntityType.SENTEK_SENSOR.getKey(),
                            new TextAttribute(group.getOid()),
                            new TextAttribute("A1"),
                            new NumberAttribute(reading.getA1()),
                            new InstantAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            fiwareEntityIntegrationService.persist(
                    tenant,
                    group,
                    new DeviceMeasurement(
                            tenant.getFiwarePrefix() + logger.getLoggerId(),
                            EntityType.SENTEK_SENSOR.getKey(),
                            new TextAttribute(group.getOid()),
                            new TextAttribute("T1"),
                            new NumberAttribute(reading.getT1()),
                            new InstantAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            fiwareEntityIntegrationService.persist(
                    tenant,
                    group,
                    new DeviceMeasurement(
                            tenant.getFiwarePrefix() + logger.getLoggerId(),
                            EntityType.SENTEK_SENSOR.getKey(),
                            new TextAttribute(group.getOid()),
                            new TextAttribute("A2"),
                            new NumberAttribute(reading.getA2()),
                            new InstantAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            fiwareEntityIntegrationService.persist(
                    tenant,
                    group,
                    new DeviceMeasurement(
                            tenant.getFiwarePrefix() + logger.getLoggerId(),
                            EntityType.SENTEK_SENSOR.getKey(),
                            new TextAttribute(group.getOid()),
                            new TextAttribute("T2"),
                            new NumberAttribute(reading.getT2()),
                            new InstantAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            fiwareEntityIntegrationService.persist(
                    tenant,
                    group,
                    new DeviceMeasurement(
                            tenant.getFiwarePrefix() + logger.getLoggerId(),
                            EntityType.SENTEK_SENSOR.getKey(),
                            new TextAttribute(group.getOid()),
                            new TextAttribute("A3"),
                            new NumberAttribute(reading.getA3()),
                            new InstantAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            fiwareEntityIntegrationService.persist(
                    tenant,
                    group,
                    new DeviceMeasurement(
                            tenant.getFiwarePrefix() + logger.getLoggerId(),
                            EntityType.SENTEK_SENSOR.getKey(),
                            new TextAttribute(group.getOid()),
                            new TextAttribute("T3"),
                            new NumberAttribute(reading.getT3()),
                            new InstantAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            fiwareEntityIntegrationService.persist(
                    tenant,
                    group,
                    new DeviceMeasurement(
                            tenant.getFiwarePrefix() + logger.getLoggerId(),
                            EntityType.SENTEK_SENSOR.getKey(),
                            new TextAttribute(group.getOid()),
                            new TextAttribute("A4"),
                            new NumberAttribute(reading.getA4()),
                            new InstantAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            fiwareEntityIntegrationService.persist(
                    tenant,
                    group,
                    new DeviceMeasurement(
                            tenant.getFiwarePrefix() + logger.getLoggerId(),
                            EntityType.SENTEK_SENSOR.getKey(),
                            new TextAttribute(group.getOid()),
                            new TextAttribute("T4"),
                            new NumberAttribute(reading.getT4()),
                            new InstantAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            fiwareEntityIntegrationService.persist(
                    tenant,
                    group,
                    new DeviceMeasurement(
                            tenant.getFiwarePrefix() + logger.getLoggerId(),
                            EntityType.SENTEK_SENSOR.getKey(),
                            new TextAttribute(group.getOid()),
                            new TextAttribute("A5"),
                            new NumberAttribute(reading.getA5()),
                            new InstantAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            fiwareEntityIntegrationService.persist(
                    tenant,
                    group,
                    new DeviceMeasurement(
                            tenant.getFiwarePrefix() + logger.getLoggerId(),
                            EntityType.SENTEK_SENSOR.getKey(),
                            new TextAttribute(group.getOid()),
                            new TextAttribute("T5"),
                            new NumberAttribute(reading.getT5()),
                            new InstantAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            fiwareEntityIntegrationService.persist(
                    tenant,
                    group,
                    new DeviceMeasurement(
                            tenant.getFiwarePrefix() + logger.getLoggerId(),
                            EntityType.SENTEK_SENSOR.getKey(),
                            new TextAttribute(group.getOid()),
                            new TextAttribute("A6"),
                            new NumberAttribute(reading.getA6()),
                            new InstantAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            fiwareEntityIntegrationService.persist(
                    tenant,
                    group,
                    new DeviceMeasurement(
                            tenant.getFiwarePrefix() + logger.getLoggerId(),
                            EntityType.SENTEK_SENSOR.getKey(),
                            new TextAttribute(group.getOid()),
                            new TextAttribute("T6"),
                            new NumberAttribute(reading.getT6()),
                            new InstantAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            fiwareEntityIntegrationService.persist(
                    tenant,
                    group,
                    new DeviceMeasurement(
                            tenant.getFiwarePrefix() + logger.getLoggerId(),
                            EntityType.SENTEK_SENSOR.getKey(),
                            new TextAttribute(group.getOid()),
                            new TextAttribute("A7"),
                            new NumberAttribute(reading.getA7()),
                            new InstantAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            fiwareEntityIntegrationService.persist(
                    tenant,
                    group,
                    new DeviceMeasurement(
                            tenant.getFiwarePrefix() + logger.getLoggerId(),
                            EntityType.SENTEK_SENSOR.getKey(),
                            new TextAttribute(group.getOid()),
                            new TextAttribute("T7"),
                            new NumberAttribute(reading.getT7()),
                            new InstantAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            fiwareEntityIntegrationService.persist(
                    tenant,
                    group,
                    new DeviceMeasurement(
                            tenant.getFiwarePrefix() + logger.getLoggerId(),
                            EntityType.SENTEK_SENSOR.getKey(),
                            new TextAttribute(group.getOid()),
                            new TextAttribute("A8"),
                            new NumberAttribute(reading.getA8()),
                            new InstantAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            fiwareEntityIntegrationService.persist(
                    tenant,
                    group,
                    new DeviceMeasurement(
                            tenant.getFiwarePrefix() + logger.getLoggerId(),
                            EntityType.SENTEK_SENSOR.getKey(),
                            new TextAttribute(group.getOid()),
                            new TextAttribute("T8"),
                            new NumberAttribute(reading.getT8()),
                            new InstantAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            fiwareEntityIntegrationService.persist(
                    tenant,
                    group,
                    new DeviceMeasurement(
                            tenant.getFiwarePrefix() + logger.getLoggerId(),
                            EntityType.SENTEK_SENSOR.getKey(),
                            new TextAttribute(group.getOid()),
                            new TextAttribute("A9"),
                            new NumberAttribute(reading.getA9()),
                            new InstantAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            fiwareEntityIntegrationService.persist(
                    tenant,
                    group,
                    new DeviceMeasurement(
                            tenant.getFiwarePrefix() + logger.getLoggerId(),
                            EntityType.SENTEK_SENSOR.getKey(),
                            new TextAttribute(group.getOid()),
                            new TextAttribute("T9"),
                            new NumberAttribute(reading.getT9()),
                            new InstantAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));
        });
    }

}
