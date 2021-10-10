package ru.otus.homework6.dao;


import ru.otus.homework6.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    Book insert(Book book);

    Book update(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    void deleteById(long id);
}