package ru.otus.homework10.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.otus.homework10.domain.Author;
import ru.otus.homework10.rest.dto.AuthorDto;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class AuthorMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    private void init() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(Author.class, AuthorDto.class);
        modelMapper.createTypeMap(AuthorDto.class, Author.class);
    }

    public AuthorDto toDto(Author author) {
        return modelMapper.map(author, AuthorDto.class);
    }

    public Author toEntity(AuthorDto authorDto) {
        return modelMapper.map(authorDto, Author.class);
    }
}