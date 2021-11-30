package ru.otus.homework11.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework11.domain.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {
}