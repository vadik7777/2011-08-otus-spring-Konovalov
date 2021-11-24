package ru.otus.homework11.repository.mongoreactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.homework11.domain.Author;

public interface AuthorReactiveRepository extends ReactiveMongoRepository<Author, String> {
}