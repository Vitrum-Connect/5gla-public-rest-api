package de.app.fivegla.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

/**
 * Represents a base entity.
 */
@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    /**
     * The id of the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The creation date of the entity.
     */
    @Version
    @Temporal(TemporalType.TIMESTAMP)
    private Date version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
