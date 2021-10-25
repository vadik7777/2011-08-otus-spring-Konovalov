package ru.otus.homework8.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework8.domain.Author;
import ru.otus.homework8.repository.AuthorRepository;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public Author save(Author author) {
        return authorRepository.saveCustom(author);
    }
}
