package ru.otus.homework8.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework8.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findAllByBookId(String bookId);

    List<Comment> findAllByBookAuthorId(String authorId);

    List<Comment> findAllByBookGenreId(String genreId);

    void deleteAllByBookId(String bookId);

    void deleteAllByBookAuthorId(String authorId);

    void deleteAllByBookGenreId(String genreId);
}