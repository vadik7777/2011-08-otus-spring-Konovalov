package ru.otus.homework8.repository;

import org.springframework.context.annotation.Lazy;
import ru.otus.homework8.domain.Book;
import ru.otus.homework8.domain.Comment;
import ru.otus.homework8.domain.Genre;

import java.util.List;

public class GenreRepositoryCustomImpl implements GenreRepositoryCustom {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    public GenreRepositoryCustomImpl( @Lazy GenreRepository genreRepository, BookRepository bookRepository,
                                      CommentRepository commentRepository) {
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Genre saveCustom(Genre genre) {

        genreRepository.save(genre);

        List<Book> books = bookRepository.findAllByGenreId(genre.getId());
        books.forEach(book -> book.setGenre(genre));
        bookRepository.saveAll(books);

        List<Comment> comments = commentRepository.findAllByBookGenreId(genre.getId());
        comments.forEach(comment -> comment.getBook().setGenre(genre));
        commentRepository.saveAll(comments);

        return genre;
    }

    @Override
    public void deleteByIdCustom(String genreId) {
        genreRepository.deleteById(genreId);
        bookRepository.deleteAllByGenreId(genreId);
        commentRepository.deleteAllByBookGenreId(genreId);
    }
}
