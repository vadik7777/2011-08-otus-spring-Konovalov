package ru.otus.project.rnis.dto.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Организация")
public class OrganizationDto {
    @Schema(description = "Идентификатор", example = "1")
    private Long id;
    @Schema(description = "Наименование", example = "Организация 1")
    private String name;
}