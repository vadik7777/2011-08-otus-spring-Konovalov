package ru.otus.homework8.service;


import ru.otus.homework8.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book save(Book book);

    Optional<Book> findById(String id);

    List<Book> findAll();

    void deleteById(String id);

}