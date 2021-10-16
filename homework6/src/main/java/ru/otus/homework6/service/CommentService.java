package ru.otus.homework6.service;

import ru.otus.homework6.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Comment insert(Comment comment);

    Comment update(Comment comment);

    Optional<Comment> getById(long id);

    List<Comment> getAll();

    void deleteById(long id);

}