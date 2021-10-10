package ru.otus.homework5.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.homework5.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Dao для работы с жанрами должно")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    @Autowired
    private GenreDao genreDao;

    @DisplayName("добавлять жанр без id")
    @Test
    void shouldCorrectInsertWithOutId() {
        Genre expectedGenre = Genre
                .builder()
                .name("genre")
                .build();
        genreDao.insertWithOutId(expectedGenre);
        Genre actualGenre = genreDao.getByName(expectedGenre.getName());
        assertThat(actualGenre).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedGenre);
    }

    @DisplayName("возвращать ожидаемый жанр по его id")
    @Test
    void shouldCorrectGetById() {
        Genre expectedGenre = Genre
                .builder()
                .id(1)
                .build();
        Genre actualGenre = genreDao.getById(1);
        assertThat(actualGenre).usingRecursiveComparison().ignoringFields("name").isEqualTo(expectedGenre);
    }

    @DisplayName("выводить ошибку если жанр не найден по id")
    @Test
    void shouldInCorrectGetById() {
        assertThatThrownBy(() -> genreDao.getById(10))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("возвращать ожидаемый жанр по его имени")
    @Test
    void shouldCorrectGetByName() {
        Genre expectedGenre = Genre
                .builder()
                .name("genre1")
                .build();
        Genre actualGenre = genreDao.getByName("genre1");
        assertThat(actualGenre).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedGenre);
    }

    @DisplayName("выводить ошибку если жанр не найден по имени")
    @Test
    void shouldInCorrectGetByName() {
        assertThatThrownBy(() -> genreDao.getByName("notCorrectGenre"))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("возвращать ожидаемый жанр по его id и имени")
    @Test
    void shouldCorrectGetByIdAndName() {
        Genre expectedGenre = Genre
                .builder()
                .id(1)
                .name("genre1")
                .build();
        Genre actualGenre = genreDao.getByIdAndName(1, "genre1");
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("выводить ошибку если жанр не найден по его id и имени")
    @Test
    void shouldInCorrectGetByIdAndName() {
        assertThatThrownBy(() -> genreDao.getByIdAndName(100, "notCorrectGenre"))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}