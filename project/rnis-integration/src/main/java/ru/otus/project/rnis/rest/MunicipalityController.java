package ru.otus.project.rnis.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.rnis.dto.rest.MunicipalityDto;
import ru.otus.project.rnis.service.MunicipalityService;

import java.util.List;

@Tag(name = "Муниципальное образование")
@RequiredArgsConstructor
@RequestMapping("/municipality")
@RestController
public class MunicipalityController {

    private final MunicipalityService municipalityService;

    @Operation(description = "Получить список муниципальных образований")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    public List<MunicipalityDto> getAll() {
        return municipalityService.findAll();
    }
}