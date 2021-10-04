package ru.otus.homework5.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.homework5.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Dao для работы с авторами должно")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDao authorDao;

    @DisplayName("добавлять автора без id")
    @Test
    void shouldCorrectInsertWithOutId() {
        Author expectedAuthor = Author
                .builder()
                .name("fio")
                .build();
        authorDao.insertWithOutId(expectedAuthor);
        Author actualAuthor = authorDao.getByName(expectedAuthor.getName());
        assertThat(actualAuthor).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedAuthor);
    }

    @DisplayName("возвращать ожидаемого автора по его id")
    @Test
    void shouldCorrectGetById() {
        Author expectedAuthor = Author
                .builder()
                .id(1)
                .build();
        Author actualAuthor = authorDao.getById(1);
        assertThat(actualAuthor).usingRecursiveComparison().ignoringFields("name").isEqualTo(expectedAuthor);
    }

    @DisplayName("выводить ошибку если автор не найден по id")
    @Test
    void shouldInCorrectGetById() {
        assertThatThrownBy(() -> authorDao.getById(10))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("возвращать ожидаемого автора по его имени")
    @Test
    void shouldCorrectGetByName() {
        Author expectedAuthor = Author
                .builder()
                .name("fio1")
                .build();
        Author actualAuthor = authorDao.getByName("fio1");
        assertThat(actualAuthor).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedAuthor);
    }

    @DisplayName("выводить ошибку если автор не найден по имени")
    @Test
    void shouldInCorrectGetByName() {
        assertThatThrownBy(() -> authorDao.getByName("notCorrectAuthor"))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("возвращать ожидаемого автора по его id и имени")
    @Test
    void shouldCorrectGetByIdAndName() {
        Author expectedAuthor = Author
                .builder()
                .id(1)
                .name("fio1")
                .build();
        Author actualAuthor = authorDao.getByIdAndName(1, "fio1");
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("выводить ошибку если автор не найден по его id и имени")
    @Test
    void shouldInCorrectGetByIdAndName() {
        assertThatThrownBy(() -> authorDao.getByIdAndName(100, "notCorrectAuthor"))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}