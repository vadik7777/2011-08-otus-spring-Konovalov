package ru.otus.homework7.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.homework7.domain.Author;
import ru.otus.homework7.domain.Book;
import ru.otus.homework7.domain.Comment;
import ru.otus.homework7.domain.Genre;

import java.util.List;

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
        val author = new Author(0, "newAuthor");
        val genre = new Genre(0, "newGenre");
        val comment = new Comment(0, "newComment", null);
        val exceptedBook = new Book(0, "newBook", author, genre, List.of(comment));
        exceptedBook.getComments().get(0).setBook(exceptedBook);
        bookRepository.save(exceptedBook);
        testEntityManager.detach(exceptedBook);
        val actualBook = bookRepository.findById(exceptedBook.getId()).orElseThrow();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(exceptedBook);
    }

    @DisplayName("обновлять книгу")
    @Test
    void shouldCorrectUpdate() {
        val exceptedBook = bookRepository.findById(1L).orElseThrow();
        exceptedBook.setName("newBook");
        testEntityManager.detach(exceptedBook);
        bookRepository.save(exceptedBook);
        val actualBook = testEntityManager.find(Book.class, exceptedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().ignoringFields("comments").isEqualTo(exceptedBook);
    }

    @DisplayName("получать книгу по id")
    @Test
    void shouldCorrectFindById() {
        val actualBook = bookRepository.findById(1L).orElseThrow();
        testEntityManager.detach(actualBook);
        val exceptedBook = testEntityManager.find(Book.class, 1L);
        assertThat(actualBook).usingRecursiveComparison().ignoringFields("comments").isEqualTo(exceptedBook);
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldCorrectFindAll() {
        val books = bookRepository.findAll();
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
        bookRepository.deleteById(1L);
        testEntityManager.flush();
        val deletedBook = testEntityManager.find(Book.class, 1L);
        assertThat(deletedBook).isNull();
    }
}