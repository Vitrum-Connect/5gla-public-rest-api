package de.app.fivegla.persistence;

import de.app.fivegla.persistence.entity.LastRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the last-run entity.
 */
@Repository
public interface LastRunRepository extends JpaRepository<LastRun, Long> {

}
