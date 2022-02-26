package ru.otus.project.rnis.dto.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransportUnitDto {
    private Long id;
    private String objectName;
    private String phoneOfResponsible;
    private Instant informationDate;
    private Double longitude;
    private Double latitude;
    private String speed;
    private Double direction;
    private MunicipalityDto municipality;
    private TransportTypeDto transportType;
    private OrganizationDto organization;

    public TransportUnitDto(Long id) {
        this.id = id;
    }

    public TransportUnitDto(Long id, Double longitude, Double latitude, Double direction,
                            TransportTypeDto transportType) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.direction = direction;
        this.transportType = transportType;
    }
}