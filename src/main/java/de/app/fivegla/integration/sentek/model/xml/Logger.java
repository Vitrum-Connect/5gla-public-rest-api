package de.app.fivegla.integration.sentek.model.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(of = {"id", "loggerId"})
public class Logger {
    @XmlAttribute(name = "id")
    private int id;
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "logger_id")
    private String loggerId;
    @XmlAttribute(name = "latitude")
    private double latitude;
    @XmlAttribute(name = "longitude")
    private double longitude;
    @XmlElement(name = "Site")
    private Site site;
}
