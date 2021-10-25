package ru.otus.homework8.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import ru.otus.homework8.domain.Author;
import ru.otus.homework8.domain.Book;
import ru.otus.homework8.domain.Comment;

import java.util.List;

@RequiredArgsConstructor
public class AuthorRepositoryCustomImpl implements AuthorRepositoryCustom {

    @Autowired
    @Lazy
    private AuthorRepository authorRepository;

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

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