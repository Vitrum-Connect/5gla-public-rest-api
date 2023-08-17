package de.app.fivegla.integration.sentek.model.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Sensor {
    @XmlAttribute
    private String name;
    @XmlAttribute
    private int depth_cm;
    @XmlAttribute
    private String type;
    @XmlAttribute
    private String description;
    @XmlAttribute
    private String unit;
    @XmlAttribute
    private int minimum;
    @XmlAttribute
    private int maximum;
    @XmlAttribute
    private int invalid;
}
