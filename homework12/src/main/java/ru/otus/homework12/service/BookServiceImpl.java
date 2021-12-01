package ru.otus.homework12.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework12.domain.Book;
import ru.otus.homework12.exception.NotFoundException;
import ru.otus.homework12.mapper.BookMapper;
import ru.otus.homework12.repository.BookRepository;
import ru.otus.homework12.dto.BookDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Transactional
    @Override
    public BookDto create(BookDto bookDto) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toEntity(bookDto)));
    }

    @Transactional
    @Override
    public Optional<BookDto> update(long id, BookDto bookDto) {
        return bookRepository.findById(id)
                .map(book -> {
                    Book updateBook = bookMapper.toEntity(bookDto);
                    BeanUtils.copyProperties(updateBook, book);
                    return bookMapper.toDto(book);
                });
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<BookDto> findById(long id) {
        return bookRepository.findById(id).map(bookMapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        try {
            bookRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }
}