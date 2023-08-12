package de.app.fivegla.event;

import de.app.fivegla.api.Manufacturer;

/**
 * Event for data import.
 *
 * @param manufacturer the manufacturer
 */
public record DataImportEvent(Manufacturer manufacturer) {
}
