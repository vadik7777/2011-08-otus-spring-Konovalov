package ru.otus.homework10.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework10.rest.dto.GenreDto;
import ru.otus.homework10.service.GenreService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class GenreController {

    private final GenreService genreService;

    @GetMapping("api/genre")
    public List<GenreDto> getAll(){
        return genreService.findAll();
    }
}