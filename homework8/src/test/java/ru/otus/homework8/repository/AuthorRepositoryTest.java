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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@DataMongoTest
@DisplayName("Репозиторий для работы с авторами должен")
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("добавлять автора")
    @Test
    void shouldCorrectInsert() {
        val expectedAuthor = new Author("expected_author");
        authorRepository.save(expectedAuthor);

        val actualAuthor = authorRepository.findById(expectedAuthor.getId());
        assertThat(actualAuthor).isPresent().get().usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("обновлять автора")
    @Test
    void shouldCorrectUpdate() {
        val expectedAuthor = new Author("id_author1", "expected_author");
        val expectedGenre = new Genre("id_genre1", "genre1");
        val expectedBook = new Book("id_book1", "book1", expectedAuthor, expectedGenre);
        val expectedComment1 = new Comment("id_comment1", "comment1", expectedBook);
        val expectedComment2 = new Comment("id_comment2", "comment2", expectedBook);

        authorRepository.saveCustom(expectedAuthor);

        val actualAuthor = authorRepository.findById(expectedAuthor.getId());
        val actualGenre = genreRepository.findById(expectedGenre.getId());
        val actualBook = bookRepository.findById(expectedBook.getId());
        val actualComment1 = commentRepository.findById(expectedComment1.getId());
        val actualComment2 = commentRepository.findById(expectedComment2.getId());
        assertAll(
                () -> assertThat(actualAuthor).isPresent().get().usingRecursiveComparison().isEqualTo(expectedAuthor),
                () -> assertThat(actualGenre).isPresent().get().usingRecursiveComparison().isEqualTo(expectedGenre),
                () -> assertThat(actualBook).isPresent().get().usingRecursiveComparison().isEqualTo(expectedBook),
                () -> assertThat(actualComment1).isPresent().get().usingRecursiveComparison().isEqualTo(expectedComment1),
                () -> assertThat(actualComment2).isPresent().get().usingRecursiveComparison().isEqualTo(expectedComment2)
        );
    }

    @DisplayName("получать автора по id")
    @Test
    void shouldCorrectFindById() {
        val expectedAuthor = new Author("id_author2", "author2");

        val actualAuthor = authorRepository.findById(expectedAuthor.getId());
        assertThat(actualAuthor).isPresent().get().usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("должен загружать список всех авторов")
    @Test
    void shouldCorrectFindAll() {
        val authors = authorRepository.findAll();
        assertThat(authors).isNotNull().hasSizeGreaterThan(0)
                .allMatch(a -> !a.getName().equals(""));
    }

    @DisplayName("удалять автора по id")
    @Test
    void shouldCorrectDeleteById() {

        val deleteAuthorId = "id_author3";
        authorRepository.deleteByIdCustom(deleteAuthorId);

        val expectedAuthor = authorRepository.findById(deleteAuthorId);
        val expectedBooks = bookRepository.findAllByAuthorId(deleteAuthorId);
        val expectedComments = commentRepository.findAllByBookAuthorId(deleteAuthorId);

        assertAll(
                () -> assertThat(expectedAuthor).isEmpty(),
                () -> assertThat(expectedBooks).isEmpty(),
                () -> assertThat(expectedComments).isEmpty()
        );
    }
}