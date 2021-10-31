package ru.otus.homework8.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework8.domain.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {

    List<Book> findAllByAuthorId(String authorId);

    List<Book> findAllByGenreId(String genreId);

    void deleteAllByAuthorId(String authorId);

    void deleteAllByGenreId(String genreId);
}