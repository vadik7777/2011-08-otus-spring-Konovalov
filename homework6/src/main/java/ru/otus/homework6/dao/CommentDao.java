package ru.otus.homework6.dao;

import ru.otus.homework6.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {

    Comment insert(Comment comment);

    Comment update(Comment comment);

    Optional<Comment> getById(long id);

    List<Comment> getAll();

    void deleteById(long id);

}