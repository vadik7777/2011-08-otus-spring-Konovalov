package ru.otus.homework5.dao;

import ru.otus.homework5.domain.Author;

public interface AuthorDao {

    Author insertWithOutId(Author author);

    Author getById(long id);

    Author getByName(String name);

    Author getByIdAndName(long id, String name);
}