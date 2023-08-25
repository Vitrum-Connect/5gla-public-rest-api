package de.app.fivegla.integration.weenat.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Measurements {
    private final Plot plot;
    private final List<Measurement> measurements;
}
