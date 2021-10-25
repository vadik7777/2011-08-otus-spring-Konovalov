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
@DisplayName("Репозиторий для работы с книгами должен")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("добавлять книгу")
    @Test
    void shouldCorrectInsert() {
        val author = new Author("newAuthor");
        authorRepository.save(author);
        val genre = new Genre("newGenre");
        genreRepository.save(genre);
        val exceptedBook = new Book("newBook", author, genre);
        bookRepository.save(exceptedBook);

        val actualBook = bookRepository.findById(exceptedBook.getId()).orElseThrow();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(exceptedBook);
    }

    @DisplayName("обновлять книгу")
    @Test
    void shouldCorrectUpdate() {
        val expectedGenre = new Genre("id_genre1", "genre1");
        val expectedAuthor = new Author("id_author1", "author1");
        val expectedBook = new Book("id_book1", "expected_book", expectedAuthor, expectedGenre);
        val expectedComment1 = new Comment("id_comment1", "comment1", expectedBook);
        val expectedComment2 = new Comment("id_comment2", "comment2", expectedBook);

        bookRepository.saveCustom(expectedBook);

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

    @DisplayName("получать книгу по id")
    @Test
    void shouldCorrectFindById() {
        val expectedGenre = new Genre("id_genre2", "genre2");
        val expectedAuthor = new Author("id_author2", "author2");
        val expectedBook = new Book("id_book2", "book2", expectedAuthor, expectedGenre);

        val actualBook = bookRepository.findById(expectedBook.getId());
        assertThat(actualBook).isPresent().get().usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldCorrectFindAll() {
        val books = bookRepository.findAll();
        assertThat(books).isNotNull().hasSizeGreaterThan(0)
                .allMatch(b -> !b.getName().equals(""))
                .allMatch(b -> b.getAuthor() != null)
                .allMatch(b -> b.getGenre() != null);
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldCorrectDeleteById() {
        val deleteBookId = "id_book3";
        bookRepository.deleteByIdCustom(deleteBookId);

        val expectedBooks = bookRepository.findById(deleteBookId);
        val expectedComments = commentRepository.findAllByBookId(deleteBookId);

        assertAll(
                () -> assertThat(expectedBooks).isEmpty(),
                () -> assertThat(expectedComments).isEmpty()
        );
    }
}