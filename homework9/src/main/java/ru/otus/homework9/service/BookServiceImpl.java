package ru.otus.homework9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework9.repository.BookRepository;
import ru.otus.homework9.domain.Book;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Transactional
    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}