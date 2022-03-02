package ru.otus.project.rnis.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.rnis.dto.rest.NavigationInformationDto;
import ru.otus.project.rnis.service.NavigationInformationService;

@Tag(name = "Навигационная информация")
@RequiredArgsConstructor
@RequestMapping("/navigation_information")
@RestController
public class NavigationInformationController {

    private final NavigationInformationService navigationInformationService;

    @Operation(description = "Получить список навигационной информации по идентификатору транспортной единицы")
    @ApiResponse(responseCode = "200", description = "OK")
    @Parameter(name = "id", description = "Идентификатор транспортной единицы", example = "1")
    @GetMapping("/{id}")
    public Page<NavigationInformationDto> getAll(@PathVariable @NonNull Long id,
                                                  @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
                                                  @ParameterObject Pageable pageable) {
        return navigationInformationService.findAllByTransportUnitId(id, pageable);
    }
}