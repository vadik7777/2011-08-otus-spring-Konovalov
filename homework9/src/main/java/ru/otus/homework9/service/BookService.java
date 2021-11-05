package ru.otus.homework9.service;


import ru.otus.homework9.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book save(Book book);

    Optional<Book> findById(long id);

    List<Book> findAll();

    void deleteById(long id);

}