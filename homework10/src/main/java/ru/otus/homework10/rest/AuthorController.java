package ru.otus.homework10.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework10.rest.dto.AuthorDto;
import ru.otus.homework10.service.AuthorService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("api/author")
    public List<AuthorDto> getAll(){
        return authorService.findAll();
    }
}
