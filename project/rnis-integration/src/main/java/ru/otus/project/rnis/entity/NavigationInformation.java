package ru.otus.project.rnis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "navigation_information")
public class NavigationInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
    private TransportUnit transportUnit;

    public NavigationInformation(Instant informationDate, Double longitude, Double latitude, String speed,
                                 Double direction, TransportUnit transportUnit) {
        this.informationDate = informationDate;
        this.longitude = longitude;
        this.latitude = latitude;
        this.speed = speed;
        this.direction = direction;
        this.transportUnit = transportUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NavigationInformation that = (NavigationInformation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}