package ru.otus.homework9.service;

import ru.otus.homework9.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Comment save(Comment comment);

    Optional<Comment> findById(long id);

    List<Comment> findAll();

    void deleteById(long id);

    List<Comment> findCommentByBookId(long bookId);

}