package ru.otus.project.rnis.dto.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Транспортная единица (облегченный вариант)")
public class TransportUnitSimpleDto {
    @Schema(description = "Идентификатор", example = "1")
    private Long id;
    @Schema(description = "Долгота", example = "45.1234")
    private Double longitude;
    @Schema(description = "Широта", example = "45.1234")
    private Double latitude;
    @Schema(description = "Направление движения в градусах относительно севера", example = "100.1")
    private Double direction;
    @Schema(description = "Транспортный тип")
    private TransportTypeDto transportType;
}