package ru.otus.project.rnis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Entity
@Table(name = "transport_unit")
public class TransportUnit {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "object_name")
    private String objectName;

    @Column(name = "phone_of_responsible")
    private String phoneOfResponsible;

    @Column(name = "information_date")
    private Instant informationDate;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "speed")
    private String speed;

    @Column(name = "direction")
    private Double direction;

    @ManyToOne(fetch = FetchType.LAZY)
    private Municipality municipality;

    @ManyToOne(fetch = FetchType.LAZY)
    private TransportType transportType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportUnit that = (TransportUnit) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}