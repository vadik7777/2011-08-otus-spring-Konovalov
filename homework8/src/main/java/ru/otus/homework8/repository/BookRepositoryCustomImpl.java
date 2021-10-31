package ru.otus.homework8.repository;

import org.springframework.context.annotation.Lazy;
import ru.otus.homework8.domain.Book;
import ru.otus.homework8.domain.Comment;

import java.util.List;

public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    public BookRepositoryCustomImpl(@Lazy BookRepository bookRepository, CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Book saveCustom(Book book) {
        bookRepository.save(book);
        List<Comment> bookComments = commentRepository.findAllByBookId(book.getId());
        bookComments.forEach(comment -> comment.setBook(book));
        commentRepository.saveAll(bookComments);
        return book;
    }

    @Override
    public void deleteByIdCustom(String bookId) {
        bookRepository.deleteById(bookId);
        commentRepository.deleteAllByBookId(bookId);
    }
}
