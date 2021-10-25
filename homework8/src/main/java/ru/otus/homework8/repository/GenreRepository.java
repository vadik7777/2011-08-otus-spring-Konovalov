package ru.otus.homework8.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework8.domain.Genre;

public interface GenreRepository extends MongoRepository<Genre, String>, GenreRepositoryCustom {
}