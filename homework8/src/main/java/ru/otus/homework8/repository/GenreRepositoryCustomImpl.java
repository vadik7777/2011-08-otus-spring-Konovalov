package ru.otus.homework8.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import ru.otus.homework8.domain.Book;
import ru.otus.homework8.domain.Comment;
import ru.otus.homework8.domain.Genre;

import java.util.List;

@RequiredArgsConstructor
public class GenreRepositoryCustomImpl implements GenreRepositoryCustom {

    @Autowired
    @Lazy
    private GenreRepository genreRepository;

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

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
