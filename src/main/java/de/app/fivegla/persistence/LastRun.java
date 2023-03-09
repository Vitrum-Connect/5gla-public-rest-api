package de.app.fivegla.persistence;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Last run of scheduled data import, stored within the database.
 */
@Getter
@Setter
public class LastRun {
    private Instant lastRun;
}
