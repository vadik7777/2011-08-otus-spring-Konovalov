package ru.otus.project.rnis.dto.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Транспортная единица")
public class TransportUnitDto {
    @Schema(description = "Идентификатор", example = "1")
    private Long id;
    @Schema(description = "Наименование", example = "Транспортная единица 1")
    private String objectName;
    @Schema(description = "Телефон ответственного", example = "+79041708083")
    private String phoneOfResponsible;
    @Schema(description = "Дата и время получения информации", example = "2022-03-01T06:41:37Z")
    private Instant informationDate;
    @Schema(description = "Долгота", example = "45.1234")
    private Double longitude;
    @Schema(description = "Широта", example = "45.1234")
    private Double latitude;
    @Schema(description = "Скорость", example = "100 км/ч")
    private String speed;
    @Schema(description = "Направление движения в градусах относительно севера", example = "100.1")
    private Double direction;
    @Schema(description = "Муниципальное образование")
    private MunicipalityDto municipality;
    @Schema(description = "Транспортный тип")
    private TransportTypeDto transportType;
    @Schema(description = "Организация")
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