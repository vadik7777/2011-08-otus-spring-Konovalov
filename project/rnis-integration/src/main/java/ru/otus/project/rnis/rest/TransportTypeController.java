package ru.otus.project.rnis.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.rnis.dto.rest.TransportTypeDto;
import ru.otus.project.rnis.service.TransportTypeService;

import java.util.List;

@Tag(name = "Транспортный тип")
@RequiredArgsConstructor
@RequestMapping("/transport_type")
@RestController
public class TransportTypeController {

    private final TransportTypeService transportTypeService;

    @Operation(description = "Получить список транспортных типов")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    public List<TransportTypeDto> getAll() {
        return transportTypeService.findAll();
    }
}