package ru.otus.homework8.repository;

import ru.otus.homework8.domain.Book;

public interface BookRepositoryCustom {

    Book saveCustom(Book book);

    void deleteByIdCustom(String bookId);
}
