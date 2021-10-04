package ru.otus.homework5.service;

import ru.otus.homework5.domain.Genre;

public interface GenreService {

    Genre insertWithOutId(Genre genre);

    Genre getById(long id);

    Genre getByName(String name);

    Genre getByIdAndName(long id, String name);
}