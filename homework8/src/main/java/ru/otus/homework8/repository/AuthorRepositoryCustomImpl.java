package ru.otus.homework8.repository;

import org.springframework.context.annotation.Lazy;
import ru.otus.homework8.domain.Author;
import ru.otus.homework8.domain.Book;
import ru.otus.homework8.domain.Comment;

import java.util.List;

public class AuthorRepositoryCustomImpl implements AuthorRepositoryCustom {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    public AuthorRepositoryCustomImpl(@Lazy AuthorRepository authorRepository, BookRepository bookRepository,
                                       CommentRepository commentRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Author saveCustom(Author author) {

        authorRepository.save(author);

        List<Book> books = bookRepository.findAllByAuthorId(author.getId());
        books.forEach(book -> book.setAuthor(author));
        bookRepository.saveAll(books);

        List<Comment> comments = commentRepository.findAllByBookAuthorId(author.getId());
        comments.forEach(comment -> comment.getBook().setAuthor(author));
        commentRepository.saveAll(comments);

        return author;
    }

    @Override
    public void deleteByIdCustom(String authorId) {
        authorRepository.deleteById(authorId);
        bookRepository.deleteAllByAuthorId(authorId);
        commentRepository.deleteAllByBookAuthorId(authorId);
    }
}