package ru.otus.homework5.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework5.dao.AuthorDao;
import ru.otus.homework5.domain.Author;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public Author insertWithOutId(Author author) {
        return authorDao.insertWithOutId(author);
    }

    @Override
    public Author getById(long id) {
        return authorDao.getById(id);
    }

    @Override
    public Author getByName(String name) {
        return authorDao.getByName(name);
    }

    @Override
    public Author getByIdAndName(long id, String name) {
        return authorDao.getByIdAndName(id, name);
    }
}