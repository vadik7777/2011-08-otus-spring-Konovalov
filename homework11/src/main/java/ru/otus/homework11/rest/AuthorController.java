package ru.otus.homework11.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.homework11.rest.dto.AuthorDto;
import ru.otus.homework11.mapper.AuthorMapper;
import ru.otus.homework11.repository.mongoreactive.AuthorReactiveRepository;

@RequiredArgsConstructor
@RestController
public class AuthorController {

    private final AuthorReactiveRepository authorReactiveRepository;
    private final AuthorMapper authorMapper;

    @GetMapping("api/author")
    public Flux<AuthorDto> getAll(){
        return authorReactiveRepository.findAll().map(authorMapper::toDto);
    }
}