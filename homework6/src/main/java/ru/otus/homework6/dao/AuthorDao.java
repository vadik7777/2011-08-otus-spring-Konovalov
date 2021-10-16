package ru.otus.homework6.dao;


import ru.otus.homework6.domain.Author;

import java.util.Optional;

public interface AuthorDao {

    Author insert(Author author);

    Author update(Author author);

    Optional<Author> getById(long id);
}