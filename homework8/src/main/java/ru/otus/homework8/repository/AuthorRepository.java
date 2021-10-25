package ru.otus.homework8.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework8.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String>, AuthorRepositoryCustom {
}