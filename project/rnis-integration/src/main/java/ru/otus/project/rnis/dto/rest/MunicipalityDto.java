package ru.otus.project.rnis.dto.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Муниципальное образование")
public class MunicipalityDto {
    @Schema(description = "Идентификатор", example = "1")
    private Long id;
    @Schema(description ="Наименование", example = "Муниципальное образование 1")
    private String name;
}