package ru.otus.homework7.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.homework7.domain.Author;

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
        val exceptedAuthor = new Author(0, "exceptedAuthor");
        authorRepository.save(exceptedAuthor);
        testEntityManager.detach(exceptedAuthor);
        val actualAuthor = testEntityManager.find(Author.class, exceptedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(exceptedAuthor);
    }

    @DisplayName("обновлять автора")
    @Test
    void shouldCorrectUpdate() {
        val exceptedAuthor = new Author(1, "exceptedAuthor");
        authorRepository.save(exceptedAuthor);
        val actualAuthor = testEntityManager.find(Author.class, exceptedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(exceptedAuthor);
    }

    @DisplayName("получать автора по id")
    @Test
    void shouldCorrectFindById() {
        val exceptedAuthor = new Author(1, "fio1");
        val actualAuthor = authorRepository.findById(1L);
        assertThat(actualAuthor).isPresent().get().usingRecursiveComparison().isEqualTo(exceptedAuthor);
    }
}