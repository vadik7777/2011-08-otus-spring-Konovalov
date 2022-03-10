package ru.otus.project.rnis.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.otus.project.rnis.dto.rest.TransportUnitDto;
import ru.otus.project.rnis.dto.rest.TransportUnitSimpleDto;
import ru.otus.project.rnis.model.TransportUnitFilter;
import ru.otus.project.rnis.service.TransportUnitService;
import ru.otus.project.rnis.specification.TransportUnitSpecification;

import java.util.List;

@Tag(name = "Транспортная единица")
@RequiredArgsConstructor
@RequestMapping("/transport_unit")
@RestController
public class TransportUnitController {

    private final static String NOT_FOUND_MESSAGE = "Транспортная единица с id=%d не найдена";

    private final TransportUnitService transportUnitService;

    @Operation(description = "Получить список транспортных единиц")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    public List<TransportUnitSimpleDto> getAll(@ParameterObject TransportUnitFilter transportUnitFilter) {
        var spec = TransportUnitSpecification.transportUnitFilterBy(transportUnitFilter);
        return transportUnitService.findAll(spec);
    }

    @Operation(description = "Получить транспортную единицу")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = {@Content})
    })
    @Parameter(name = "id", description = "Идентификатор транспортной единицы", example = "1")
    @GetMapping("/{id}")
    public TransportUnitDto getById(@PathVariable @NonNull Long id) {
        var transportUnitDto = transportUnitService.findById(id);
        return transportUnitDto
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                               String.format(NOT_FOUND_MESSAGE, id)));
    }
}