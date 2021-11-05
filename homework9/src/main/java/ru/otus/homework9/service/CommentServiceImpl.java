package ru.otus.homework9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework9.repository.CommenRepository;
import ru.otus.homework9.domain.Comment;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommenRepository commenRepository;

    @Transactional
    @Override
    public Comment save(Comment comment) {
        return commenRepository.save(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> findById(long id) {
        return commenRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findAll() {
        return commenRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commenRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findCommentByBookId(long bookId) {
        return commenRepository.findCommentByBookId(bookId);
    }
}