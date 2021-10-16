package ru.otus.homework6.service;


import ru.otus.homework6.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book insert(Book book);

    Book update(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    void deleteById(long id);

}