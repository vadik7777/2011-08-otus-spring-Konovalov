package ru.otus.homework5.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework5.dao.GenreDao;
import ru.otus.homework5.domain.Genre;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    public Genre insertWithOutId(Genre genre) {
        return genreDao.insertWithOutId(genre);
    }

    @Override
    public Genre getById(long id) {
        return genreDao.getById(id);
    }

    @Override
    public Genre getByName(String name) {
        return genreDao.getByName(name);
    }

    @Override
    public Genre getByIdAndName(long id, String name) {
        return genreDao.getByIdAndName(id, name);
    }
}