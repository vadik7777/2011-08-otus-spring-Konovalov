package ru.otus.project.rnis.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Фильтр транспортных единиц")
public class TransportUnitFilter {
    @Schema(description = "Идентификатор транспортного типа", example = "31")
    private Long transportTypeId;
    @Schema(description = "Идентификатор муниципального образования", example = "363")
    private Long municipalityId;
    @Schema(description = "Дата и время получения информации 'ОТ'", example = "2022-03-01T06:41:37Z")
    private Instant informationDateFrom;
}