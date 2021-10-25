package ru.otus.homework8.repository;

import ru.otus.homework8.domain.Author;

public interface AuthorRepositoryCustom {

    Author saveCustom(Author author);

    void deleteByIdCustom(String authorId);
}
