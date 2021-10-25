package ru.otus.homework8.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import ru.otus.homework8.domain.Book;
import ru.otus.homework8.domain.Comment;

import java.util.List;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    @Lazy
    @Autowired
    private BookRepository bookRepository;

    private final CommentRepository commentRepository;

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
