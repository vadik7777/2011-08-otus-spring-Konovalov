package ru.otus.homework11.repository;

import lombok.val;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import reactor.test.StepVerifier;
import ru.otus.homework11.domain.Author;
import ru.otus.homework11.domain.Book;
import ru.otus.homework11.domain.Genre;
import ru.otus.homework11.repository.mongoreactive.BookReactiveRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@DisplayName("Репозиторий для работы с книгами должен")
class BookRepositoryTest {

    @Autowired
    private BookReactiveRepository bookReactiveRepository;

    @DisplayName("добавлять книгу")
    @Test
    void shouldCorrectInsert() {
        val expectedAuthor = new Author("id_author1", "author1");
        val expectedGenre = new Genre("id_genre1", "genre1");
        val expectedBook = new Book("book4", expectedAuthor, expectedGenre);

        val bookMono = bookReactiveRepository.save(expectedBook)
                .flatMap(actualBook -> bookReactiveRepository.findById(actualBook.getId()));

        StepVerifier.create(bookMono)
                .assertNext(actualBook -> assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook))
                .expectComplete()
                .verify();
    }

    @DisplayName("обновлять книгу")
    @Test
    void shouldCorrectUpdate() {
        val expectedGenre = new Genre("id_genre1", "genre1");
        val expectedAuthor = new Author("id_author1", "author1");
        val expectedBook = new Book("id_book1", "expected_book", expectedAuthor, expectedGenre);

        val bookMono = bookReactiveRepository.save(expectedBook)
                .then(bookReactiveRepository.findById(expectedBook.getId()));

        StepVerifier.create(bookMono)
                .assertNext(actualBook -> assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook))
                .expectComplete()
                .verify();
    }

    @DisplayName("получать книгу по id")
    @Test
    void shouldCorrectFindById() {
        val expectedGenre = new Genre("id_genre2", "genre2");
        val expectedAuthor = new Author("id_author2", "author2");
        val expectedBook = new Book("id_book2", "book2", expectedAuthor, expectedGenre);

        val bookMono = bookReactiveRepository.findById(expectedBook.getId());

        StepVerifier.create(bookMono)
                .assertNext(actualBook -> assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook))
                .expectComplete()
                .verify();
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldCorrectFindAll() {
        val expectedBook = new Book("id_book1", "book1",
                        new Author("id_author1", "author1"),
                        new Genre("id_genre1", "genre1"));

        val bookFlux = bookReactiveRepository.findAll();

        StepVerifier.create(bookFlux)
                .assertNext(actualBook -> assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook))
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldCorrectDeleteById() {
        val deleteBookId = "id_book3";

        val bookMono = bookReactiveRepository.deleteById(deleteBookId)
                .then(bookReactiveRepository.findById(deleteBookId));

        StepVerifier.create(bookMono)
                .expectComplete()
                .verify();
    }
}