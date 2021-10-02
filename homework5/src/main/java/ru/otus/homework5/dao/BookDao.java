package ru.otus.homework5.dao;

import ru.otus.homework5.domain.Book;

import java.util.List;

public interface BookDao {

    Book insert(Book book);

    Book update(Book book);

    Book getById(long id);

    List<Book> getAll();

    void deleteById(long id);
}