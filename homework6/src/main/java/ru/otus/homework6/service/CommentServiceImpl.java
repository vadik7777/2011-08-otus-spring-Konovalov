package ru.otus.homework6.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework6.dao.CommentDao;
import ru.otus.homework6.domain.Comment;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    @Transactional
    @Override
    public Comment insert(Comment comment) {
        return commentDao.insert(comment);
    }

    @Transactional
    @Override
    public Comment update(Comment comment) {
        return commentDao.update(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> getById(long id) {
        return commentDao.getById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getAll() {
        return commentDao.getAll();
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentDao.deleteById(id);
    }
}