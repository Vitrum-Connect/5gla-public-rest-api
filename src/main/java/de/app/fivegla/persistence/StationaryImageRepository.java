package de.app.fivegla.persistence;

import de.app.fivegla.persistence.entity.StationaryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the stationary image entity.
 */
@Repository
public interface StationaryImageRepository extends JpaRepository<StationaryImage, Long> {
}
