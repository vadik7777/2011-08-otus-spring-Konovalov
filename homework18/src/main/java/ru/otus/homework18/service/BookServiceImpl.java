package ru.otus.homework18.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework18.domain.Book;
import ru.otus.homework18.dto.BookDto;
import ru.otus.homework18.exception.NotFoundException;
import ru.otus.homework18.mapper.BookMapper;
import ru.otus.homework18.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final SleepService sleepService;

    @HystrixCommand(groupKey = "book", commandKey = "createBook", fallbackMethod = "createFallbackMethod")
    @Transactional
    @Override
    public BookDto create(BookDto bookDto) {
        bookDto = bookMapper.toDto(bookRepository.save(bookMapper.toEntity(bookDto)));
        return bookDto;
    }

    @Override
    public BookDto createFallbackMethod(BookDto bookDto) {
        return new BookDto();
    }

    @HystrixCommand(groupKey = "book", commandKey = "updateBook", fallbackMethod = "updateFallbackMethod")
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

    @Override
    public Optional<BookDto> updateFallbackMethod(long id, BookDto bookDto) {
        return Optional.of(new BookDto());
    }

    @HystrixCommand(groupKey = "book", commandKey = "findByIdBook", fallbackMethod = "findByIdFallbackMethod")
    @Transactional(readOnly = true)
    @Override
    public Optional<BookDto> findById(long id) {
        return bookRepository.findById(id).map(bookMapper::toDto);
    }

    @Override
    public Optional<BookDto> findByIdFallbackMethod(long id) {
        return Optional.of(new BookDto());
    }

    @HystrixCommand(groupKey = "book", commandKey = "findAllBook", fallbackMethod = "findAllFallbackMethod")
    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {
        sleepService.sleepRandomly();
        return bookRepository.findAll().stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> findAllFallbackMethod() {
        return List.of();
    }

    @HystrixCommand(groupKey = "book", commandKey = "deleteBook", fallbackMethod = "deleteAllFallbackMethod")
    @Transactional
    @Override
    public void delete(BookDto bookDto) {
        try {
            bookRepository.deleteById(bookDto.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }

    @Override
    public void deleteAllFallbackMethod(BookDto bookDto) {
    }
}