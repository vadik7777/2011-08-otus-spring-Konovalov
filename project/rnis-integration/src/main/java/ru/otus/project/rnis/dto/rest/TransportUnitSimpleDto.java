package ru.otus.project.rnis.dto.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransportUnitSimpleDto {
    private Long id;
    private Double longitude;
    private Double latitude;
    private Double direction;
    private TransportTypeDto transportType;
}