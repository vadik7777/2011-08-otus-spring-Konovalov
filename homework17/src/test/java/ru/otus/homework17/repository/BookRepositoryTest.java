package ru.otus.homework17.repository;

import lombok.val;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.homework17.domain.Author;
import ru.otus.homework17.domain.Book;
import ru.otus.homework17.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с книгами должен")
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("добавлять книгу")
    @Test
    void shouldCorrectInsert() {
        val author = new Author(1L, "fio1");
        val genre = new Genre(1L, "genre1");
        val expectedBook = new Book(0, "newBook", author, genre);
        bookRepository.save(expectedBook);
        testEntityManager.detach(expectedBook);
        val actualBook = bookRepository.findById(expectedBook.getId()).orElseThrow();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("обновлять книгу")
    @Test
    void shouldCorrectUpdate() {
        val expectedBook = bookRepository.findById(1L).orElseThrow();
        expectedBook.setName("newBook");
        expectedBook.setGenre(new Genre(3L, "genre3"));
        testEntityManager.detach(expectedBook);
        bookRepository.save(expectedBook);
        val actualBook = bookRepository.findById(1L).orElseThrow();
        actualBook.setGenre((Genre) Hibernate.unproxy(actualBook.getGenre()));
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("получать книгу по id")
    @Test
    void shouldCorrectFindById() {
        val actualBook = bookRepository.findById(1L).orElseThrow();
        testEntityManager.detach(actualBook);
        val expectedBook = testEntityManager.find(Book.class, 1L);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldCorrectFindAll() {
        val books = bookRepository.findAll();
        assertThat(books).isNotNull().hasSize(3)
                .allMatch(b -> !b.getName().equals(""))
                .allMatch(b -> b.getAuthor() != null)
                .allMatch(b -> b.getGenre() != null);
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldCorrectDeleteById() {
        val deleteBook = testEntityManager.find(Book.class, 1L);
        assertThat(deleteBook).isNotNull();
        bookRepository.deleteById(1L);
        testEntityManager.flush();
        val deletedBook = testEntityManager.find(Book.class, 1L);
        assertThat(deletedBook).isNull();
    }
}