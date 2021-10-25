package ru.otus.homework8.service;

import ru.otus.homework8.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Comment save(Comment comment);

    Optional<Comment> findById(String id);

    List<Comment> findAll();

    void deleteById(String id);

    List<Comment> findCommentByBookId(String bookId);

}