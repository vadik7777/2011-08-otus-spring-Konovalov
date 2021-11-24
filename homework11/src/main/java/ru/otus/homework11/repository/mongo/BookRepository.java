package ru.otus.homework11.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework11.domain.Book;

public interface BookRepository extends MongoRepository<Book, String> {
}