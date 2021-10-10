package ru.otus.homework6.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework6.dao.BookDao;
import ru.otus.homework6.domain.Book;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Transactional
    @Override
    public Book insert(Book book) {
        return bookDao.insert(book);
    }

    @Transactional
    @Override
    public Book update(Book book) {
        return bookDao.update(book);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> getById(long id) {
        return bookDao.getById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }
}