package ru.otus.homework8.repository;

import ru.otus.homework8.domain.Genre;

public interface GenreRepositoryCustom {

    Genre saveCustom(Genre genre);

    void deleteByIdCustom(String genreId);

}
