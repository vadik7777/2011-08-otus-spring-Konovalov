package ru.otus.homework6.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework6.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с авторами должно")
@DataJpaTest
@Import(AuthorDaoRepositoryJpa.class)
class AuthorDaoRepositoryJpaTest {

    @Autowired
    private AuthorDaoRepositoryJpa authorDaoRepositoryJpa;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("добавлять автора")
    @Test
    void shouldCorrectInsert() {
        val exceptedAuthor = new Author(0, "exceptedAuthor");
        authorDaoRepositoryJpa.insert(exceptedAuthor);
        testEntityManager.detach(exceptedAuthor);
        val actualAuthor = testEntityManager.find(Author.class, exceptedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(exceptedAuthor);
    }

    @DisplayName("обновлять автора")
    @Test
    void shouldCorrectUpdate() {
        val exceptedAuthor = new Author(1, "exceptedAuthor");
        authorDaoRepositoryJpa.update(exceptedAuthor);
        val actualAuthor = testEntityManager.find(Author.class, exceptedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(exceptedAuthor);
    }

    @DisplayName("получать автора по id")
    @Test
    void shouldCorrectGetById() {
        val exceptedAuthor = new Author(1, "fio1");
        val actualAuthor = authorDaoRepositoryJpa.getById(1L);
        assertThat(actualAuthor).isPresent().get().usingRecursiveComparison().isEqualTo(exceptedAuthor);
    }
}