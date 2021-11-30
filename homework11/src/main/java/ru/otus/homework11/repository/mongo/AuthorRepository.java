package ru.otus.homework11.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework11.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}