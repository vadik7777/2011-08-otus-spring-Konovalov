package ru.otus.homework5.service;

import ru.otus.homework5.domain.Book;

import java.util.List;

public interface BookService {

    Book insert(Book book);

    Book update(Book book);

    Book getById(long id);

    List<Book> getAll();

    void deleteById(long id);

}