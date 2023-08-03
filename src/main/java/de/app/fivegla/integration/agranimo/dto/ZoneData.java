package de.app.fivegla.integration.agranimo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZoneData {
    private String location;
    private Thresholds thresholds;
    private String metric;
    private String timezone;
    private Point point;
    private Features features;
}
