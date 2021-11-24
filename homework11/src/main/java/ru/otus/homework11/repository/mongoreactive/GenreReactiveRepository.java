package ru.otus.homework11.repository.mongoreactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.homework11.domain.Genre;

public interface GenreReactiveRepository extends ReactiveMongoRepository<Genre, String> {
}