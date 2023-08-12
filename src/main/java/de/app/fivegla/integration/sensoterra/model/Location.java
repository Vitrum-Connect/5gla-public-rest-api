package de.app.fivegla.integration.sensoterra.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Domain model.
 */
@Getter
@Setter
public class Location {
    private String name;
    private int id;
    @JsonProperty("customer_id")
    private String customerId;
    private String code;
    private List<Status> status;
}
