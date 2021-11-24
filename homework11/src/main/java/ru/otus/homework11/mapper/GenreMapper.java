package ru.otus.homework11.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.otus.homework11.domain.Genre;
import ru.otus.homework11.rest.dto.GenreDto;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class GenreMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    private void init() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(Genre.class, GenreDto.class);
        modelMapper.createTypeMap(GenreDto.class, Genre.class);
    }

    public GenreDto toDto(Genre genre) {
        return modelMapper.map(genre, GenreDto.class);
    }

    public Genre toEntity(GenreDto genreDto) {
        return modelMapper.map(genreDto, Genre.class);
    }
}