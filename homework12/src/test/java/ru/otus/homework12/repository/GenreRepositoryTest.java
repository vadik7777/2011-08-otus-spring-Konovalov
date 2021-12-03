package ru.otus.homework12.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.homework12.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с жанрами должен")
@DataJpaTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("добавлять жанр")
    @Test
    void shouldCorrectInsert() {
        val expectedGenre = new Genre(0, "expectedGenre");
        genreRepository.save(expectedGenre);
        testEntityManager.detach(expectedGenre);
        val actualGenre = testEntityManager.find(Genre.class, expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);

    }

    @DisplayName("обновлять жанр")
    @Test
    void shouldCorrectUpdate() {
        val expectedGenre = new Genre(1, "expectedGenre");
        genreRepository.save(expectedGenre);
        val actualGenre = testEntityManager.find(Genre.class, expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("получать жанр по id")
    @Test
    void shouldCorrectGetById() {
        val expectedGenre = new Genre(1, "genre1");
        val actualGenre = genreRepository.findById(1L).orElseThrow();
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }
}