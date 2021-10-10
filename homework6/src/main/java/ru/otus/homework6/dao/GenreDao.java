package ru.otus.homework6.dao;

import ru.otus.homework6.domain.Genre;

import java.util.Optional;

public interface GenreDao {

    Genre insert(Genre genre);

    Genre update(Genre genre);

    Optional<Genre> getById(long id);
}