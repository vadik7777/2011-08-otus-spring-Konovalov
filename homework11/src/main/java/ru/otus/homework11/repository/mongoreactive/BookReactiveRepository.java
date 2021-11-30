package ru.otus.homework11.repository.mongoreactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.homework11.domain.Book;

public interface BookReactiveRepository extends ReactiveMongoRepository<Book, String> {
}