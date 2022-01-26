package ru.otus.homework18.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.otus.homework18.domain.Genre;
import ru.otus.homework18.dto.GenreDto;

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