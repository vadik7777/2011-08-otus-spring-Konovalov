package ru.otus.homework10.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.homework10.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с авторами должен")
@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("добавлять автора")
    @Test
    void shouldCorrectInsert() {
        val expectedAuthor = new Author(0, "expectedAuthor");
        authorRepository.save(expectedAuthor);
        testEntityManager.detach(expectedAuthor);
        val actualAuthor = testEntityManager.find(Author.class, expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("обновлять автора")
    @Test
    void shouldCorrectUpdate() {
        val expectedAuthor = new Author(1, "expectedAuthor");
        authorRepository.save(expectedAuthor);
        val actualAuthor = testEntityManager.find(Author.class, expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("получать автора по id")
    @Test
    void shouldCorrectFindById() {
        val expectedAuthor = new Author(1, "fio1");
        val actualAuthor = authorRepository.findById(1L).orElseThrow();
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }
}