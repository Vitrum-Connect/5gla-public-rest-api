package de.app.fivegla.integration.agranimo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class SoilMoisture {

    private String deviceId;
    @JsonProperty("TMS")
    private Instant tms;
    private Instant createdAt;
    private double smo1;
    private double smo2;
    private double smo3;
    private double smo4;

}
