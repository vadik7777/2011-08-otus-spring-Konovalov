package ru.otus.homework6.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework6.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с жанрами должно")
@DataJpaTest
@Import(GenreDaoJpa.class)
class GenreDaoJpaTest {

    @Autowired
    private GenreDaoJpa genreDaoJpa;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("добавлять жанр")
    @Test
    void shouldCorrectInsert() {
        val exceptedGenre = new Genre(0, "exceptedGenre");
        genreDaoJpa.insert(exceptedGenre);
        testEntityManager.detach(exceptedGenre);
        val actualGenre = testEntityManager.find(Genre.class, exceptedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(exceptedGenre);

    }

    @DisplayName("обновлять жанр")
    @Test
    void shouldCorrectUpdate() {
        val exceptedGenre = new Genre(1, "exceptedGenre");
        genreDaoJpa.update(exceptedGenre);
        val actualGenre = testEntityManager.find(Genre.class, exceptedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(exceptedGenre);
    }

    @DisplayName("получать жанр по id")
    @Test
    void shouldCorrectGetById() {
        val exceptedGenre = new Genre(1, "genre1");
        val actualGenre = genreDaoJpa.getById(1);
        assertThat(actualGenre).isPresent().get().usingRecursiveComparison().isEqualTo(exceptedGenre);
    }
}