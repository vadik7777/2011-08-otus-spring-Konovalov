package ru.otus.homework8.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework8.domain.Genre;
import ru.otus.homework8.repository.GenreRepository;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Genre save(Genre genre) {
        return genreRepository.saveCustom(genre);
    }
}
