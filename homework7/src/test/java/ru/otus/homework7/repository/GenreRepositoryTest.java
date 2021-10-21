package ru.otus.homework7.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.homework7.domain.Genre;

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
        val exceptedGenre = new Genre(0, "exceptedGenre");
        genreRepository.save(exceptedGenre);
        testEntityManager.detach(exceptedGenre);
        val actualGenre = testEntityManager.find(Genre.class, exceptedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(exceptedGenre);

    }

    @DisplayName("обновлять жанр")
    @Test
    void shouldCorrectUpdate() {
        val exceptedGenre = new Genre(1, "exceptedGenre");
        genreRepository.save(exceptedGenre);
        val actualGenre = testEntityManager.find(Genre.class, exceptedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(exceptedGenre);
    }

    @DisplayName("получать жанр по id")
    @Test
    void shouldCorrectGetById() {
        val exceptedGenre = new Genre(1, "genre1");
        val actualGenre = genreRepository.findById(1L);
        assertThat(actualGenre).isPresent().get().usingRecursiveComparison().isEqualTo(exceptedGenre);
    }
}