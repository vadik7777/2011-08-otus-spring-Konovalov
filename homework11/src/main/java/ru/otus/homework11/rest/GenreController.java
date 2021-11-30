package ru.otus.homework11.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.homework11.mapper.GenreMapper;
import ru.otus.homework11.rest.dto.GenreDto;
import ru.otus.homework11.repository.mongoreactive.GenreReactiveRepository;

@RequiredArgsConstructor
@RestController
public class GenreController {

    private final GenreReactiveRepository genreReactiveRepository;
    private final GenreMapper genreMapper;

    @GetMapping("api/genre")
    public Flux<GenreDto> getAll(){
        return genreReactiveRepository.findAll().map(genreMapper::toDto);
    }
}