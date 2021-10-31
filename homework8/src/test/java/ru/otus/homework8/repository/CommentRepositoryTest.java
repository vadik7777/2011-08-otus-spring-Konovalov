package ru.otus.homework8.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework8.domain.Author;
import ru.otus.homework8.domain.Book;
import ru.otus.homework8.domain.Comment;
import ru.otus.homework8.domain.Genre;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@DataMongoTest
@DisplayName("Репозиторий для работы с комментариями должен")
class CommentRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("добавлять комментарий")
    @Test
    void shouldCorrectInsert() {
        val author = new Author("newAuthor");
        authorRepository.save(author);
        val genre = new Genre("newGenre");
        genreRepository.save(genre);
        val exceptedBook = new Book("newBook", author, genre);
        bookRepository.save(exceptedBook);
        val expectedComment = new Comment("new comment", exceptedBook);
        commentRepository.save(expectedComment);

        val actualComment = commentRepository.findById(expectedComment.getId()).orElseThrow();
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);

    }

    @DisplayName("обновлять комментарий")
    @Test
    void shouldCorrectUpdate() {
        val expectedGenre = new Genre("id_genre1", "genre1");
        val expectedAuthor = new Author("id_author1", "author1");
        val expectedBook = new Book("id_book1", "book1", expectedAuthor, expectedGenre);
        val expectedComment = new Comment("id_comment1", "expected_comment1", expectedBook);

        commentRepository.save(expectedComment);

        val actualAuthor = authorRepository.findById(expectedAuthor.getId());
        val actualGenre = genreRepository.findById(expectedGenre.getId());
        val actualBook = bookRepository.findById(expectedBook.getId());
        val actualComment = commentRepository.findById(expectedComment.getId());
        assertAll(
                () -> assertThat(actualAuthor).isPresent().get().usingRecursiveComparison().isEqualTo(expectedAuthor),
                () -> assertThat(actualGenre).isPresent().get().usingRecursiveComparison().isEqualTo(expectedGenre),
                () -> assertThat(actualBook).isPresent().get().usingRecursiveComparison().isEqualTo(expectedBook),
                () -> assertThat(actualComment).isPresent().get().usingRecursiveComparison().isEqualTo(expectedComment)
        );
    }

    @DisplayName("получать комментарий по id")
    @Test
    void shouldCorrectFindById() {
        val expectedGenre = new Genre("id_genre2", "genre2");
        val expectedAuthor = new Author("id_author2", "author2");
        val expectedBook = new Book("id_book2", "book2", expectedAuthor, expectedGenre);
        val expectedComment = new Comment("id_comment3", "comment3", expectedBook);

        val actualComment = commentRepository.findById(expectedComment.getId());
        assertThat(actualComment).isPresent().get().usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("должен загружать список всех комментариев")
    @Test
    void shouldCorrectFindAll() {
        val comments = commentRepository.findAll();
        assertThat(comments).isNotNull().hasSizeGreaterThan(0)
                .allMatch(c -> !c.getComment().equals(""))
                .allMatch(c -> c.getBook() != null)
                .allMatch(c -> c.getBook().getAuthor() != null)
                .allMatch(c -> c.getBook().getGenre() != null);
    }

    @DisplayName("удалять комментарий по id")
    @Test
    void shouldCorrectDeleteById() {
        val deleteCommentId = "id_comment4";
        commentRepository.deleteById(deleteCommentId);
        val expectedComment = commentRepository.findById(deleteCommentId);
        assertThat(expectedComment).isEmpty();
    }

    @DisplayName("получать комментарии по id книги")
    @Test
    void shouldCorrectFindCommentByBookId() {
        val expectedGenre = new Genre("id_genre2", "genre2");
        val expectedAuthor = new Author("id_author2", "author2");
        val expectedBook = new Book("id_book2", "book2", expectedAuthor, expectedGenre);
        val expectedComment = new Comment("id_comment3", "comment3", expectedBook);

        val actualComment = commentRepository.findAllByBookId(expectedBook.getId());
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(Arrays.asList(expectedComment));
    }
}