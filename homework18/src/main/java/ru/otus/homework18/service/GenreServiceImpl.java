package ru.otus.homework18.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework18.mapper.GenreMapper;
import ru.otus.homework18.repository.GenreRepository;
import ru.otus.homework18.dto.GenreDto;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @HystrixCommand(groupKey = "genre", commandKey = "findAllGenre", fallbackMethod = "findAllFallbackMethod")
    @Transactional(readOnly = true)
    @Override
    public List<GenreDto> findAll() {
        return genreRepository.findAll().stream().map(genreMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<GenreDto> findAllFallbackMethod() {
        return List.of();
    }
}