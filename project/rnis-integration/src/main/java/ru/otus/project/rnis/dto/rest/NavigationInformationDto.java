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
public class NavigationInformationDto {
    private Long id;
    private Instant informationDate;
    private Double longitude;
    private Double latitude;
    private String speed;
    private Double direction;
    private TransportUnitDto transportUnit;
}