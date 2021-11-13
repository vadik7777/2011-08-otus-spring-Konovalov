package ru.otus.homework10.service;

import ru.otus.homework10.rest.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookDto create(BookDto bookDto);

    Optional<BookDto> update(long id, BookDto bookDto);

    Optional<BookDto> findById(long id);

    List<BookDto> findAll();

    void deleteById(long id);

}