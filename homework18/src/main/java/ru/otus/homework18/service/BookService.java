package ru.otus.homework18.service;

import ru.otus.homework18.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookDto create(BookDto bookDto);

    BookDto createFallbackMethod(BookDto bookDto);

    Optional<BookDto> update(long id, BookDto bookDto);

    Optional<BookDto> updateFallbackMethod(long id, BookDto bookDto);

    Optional<BookDto> findById(long id);

    Optional<BookDto> findByIdFallbackMethod(long id);

    List<BookDto> findAll();

    List<BookDto> findAllFallbackMethod();

    void delete(BookDto bookDto);

    void deleteAllFallbackMethod(BookDto bookDto);

}