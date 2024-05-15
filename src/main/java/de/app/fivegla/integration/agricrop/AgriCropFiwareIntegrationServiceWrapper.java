package de.app.fivegla.integration.agricrop;


import de.app.fivegla.api.enums.EntityType;
import de.app.fivegla.business.agricrop.GpsCoordinate;
import de.app.fivegla.config.InternalBeanConfiguration;
import de.app.fivegla.integration.fiware.model.AgriCrop;
import de.app.fivegla.integration.fiware.model.internal.DateTimeAttribute;
import de.app.fivegla.integration.fiware.model.internal.TextAttribute;
import de.app.fivegla.persistence.entity.Group;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AgriCropFiwareIntegrationServiceWrapper {

    private final InternalBeanConfiguration internalBeanConfiguration;

    /**
     * Persists the coordinates of a tenant's crop to the context broker.
     *
     * @param tenant      the tenant owning the crop
     * @param group       the group of the crop
     * @param cropId      the ID of the crop
     * @param coordinates the list of GPS coordinates of the crop
     * @return the persisted crop
     */
    public AgriCrop persist(Tenant tenant, Group group, String cropId, List<GpsCoordinate> coordinates) {
        var agriCrop = new AgriCrop(
                tenant.getFiwarePrefix() + cropId,
                EntityType.AGRI_CROP.getKey(),
                new TextAttribute(group.getGroupId()),
                new DateTimeAttribute(Instant.now()),
                coordinates);
        internalBeanConfiguration.fiwareEntityIntegrationService(tenant.getTenantId()).persist(agriCrop);
        return agriCrop;
    }
}
