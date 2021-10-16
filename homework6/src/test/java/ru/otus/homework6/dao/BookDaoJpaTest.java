package ru.otus.homework6.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework6.domain.Author;
import ru.otus.homework6.domain.Book;
import ru.otus.homework6.domain.Comment;
import ru.otus.homework6.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с книгами должно")
@DataJpaTest
@Import(BookDaoJpa.class)
class BookDaoJpaTest {

    @Autowired
    private BookDaoJpa bookDaoJpa;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("добавлять книгу")
    @Test
    void shouldCorrectInsert() {
        val author = new Author(0, "newAuthor");
        val genre = new Genre(0, "newGenre");
        val comment = new Comment(0, "newComment", null);
        val exceptedBook = new Book(0, "newBook", author, genre, List.of(comment));
        exceptedBook.getComments().get(0).setBook(exceptedBook);
        bookDaoJpa.insert(exceptedBook);
        testEntityManager.detach(exceptedBook);
        val actualBook = bookDaoJpa.getById(exceptedBook.getId()).orElseThrow();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(exceptedBook);
    }

    @DisplayName("обновлять книгу")
    @Test
    void shouldCorrectUpdate() {
        val exceptedBook = bookDaoJpa.getById(1L).orElseThrow();
        exceptedBook.setName("newBook");
        testEntityManager.detach(exceptedBook);
        bookDaoJpa.update(exceptedBook);
        val actualBook = bookDaoJpa.getById(1L).orElseThrow();
        assertThat(actualBook).usingRecursiveComparison().ignoringFields("comments").isEqualTo(exceptedBook);
    }

    @DisplayName("получать книгу по id")
    @Test
    void shouldCorrectGetById() {
        val actualBook = bookDaoJpa.getById(1L).orElseThrow();
        testEntityManager.detach(actualBook);
        val exceptedBook = testEntityManager.find(Book.class, 1L);
        assertThat(actualBook).usingRecursiveComparison().ignoringFields("comments").isEqualTo(exceptedBook);
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldCorrectGetAll() {
        val books = bookDaoJpa.getAll();
        assertThat(books).isNotNull().hasSize(3)
                .allMatch(b -> !b.getName().equals(""))
                .allMatch(b -> b.getAuthor() != null)
                .allMatch(b -> b.getGenre() != null)
                .allMatch(b -> b.getComments() != null && b.getComments().size() > 0);
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldCorrectDeleteById() {
        val deleteBook = testEntityManager.find(Book.class, 1L);
        assertThat(deleteBook).isNotNull();
        bookDaoJpa.deleteById(1L);
        testEntityManager.detach(deleteBook);
        val deletedBook = testEntityManager.find(Book.class, 1L);
        assertThat(deletedBook).isNull();
    }
}