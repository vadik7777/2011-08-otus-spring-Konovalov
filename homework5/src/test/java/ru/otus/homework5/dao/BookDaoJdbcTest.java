package ru.otus.homework5.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework5.domain.Author;
import ru.otus.homework5.domain.Book;
import ru.otus.homework5.domain.Genre;
import ru.otus.homework5.exception.NotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    private static final int EXPECTED_NUMBER_OF_BOOKS = 3;
    private static final int EXISTING_BOOK_ID = 1;

    @Autowired
    private BookDaoJdbc bookDao;

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldCorrectInsert() {
        Book expectedBook = Book.builder()
                .name("book")
                .author(Author.builder().id(1).name("fio1").build())
                .genre(Genre.builder().id(1).name("genre1").build())
                .build();
        Book book = bookDao.insert(expectedBook);
        expectedBook.setId(book.getId());
        Book actualBook = bookDao.getById(book.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("обновлять книгу в БД")
    @Test
    void shouldCorrectUpdateBook() {
        Book expectedBook = Book.builder()
                .id(1)
                .name("book")
                .author(Author.builder().id(1).name("fio1").build())
                .genre(Genre.builder().id(1).name("genre1").build())
                .build();
        Book book = bookDao.update(expectedBook);
        Book actualBook = bookDao.getById(book.getId());
        assertThat(actualBook).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedBook);
    }

    @DisplayName("выводить ошибку при обновлении не существующей книги в БД")
    @Test
    void shouldInCorrectUpdate() {
        Book notCorrectBook = Book.builder()
                .id(10)
                .build();
        assertThatThrownBy(() -> bookDao.update(notCorrectBook))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("возвращать ожидаемую книгу по ее id")
    @Test
    void shouldCorrectGetById() {
        Book expectedBook = new Book().builder()
                .id(1)
                .name("book1")
                .author(new Author(1, "fio1"))
                .genre(new Genre(1, "genre1"))
                .build();
        Book actualBook = bookDao.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldCorrectGetAll() {
        List<Book> books = bookDao.getAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(s -> !s.getName().equals(""))
                .allMatch(s -> s.getAuthor() != null)
                .allMatch(s -> s.getGenre() != null);
    }

    @DisplayName("удалять книгу по ее id")
    @Test
    void shouldCorrectDeleteById() {
        assertThatCode(() -> bookDao.getById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        bookDao.deleteById(EXISTING_BOOK_ID);

        assertThatThrownBy(() -> bookDao.getById(EXISTING_BOOK_ID))
                .isInstanceOf(NotFoundException.class);
    }
}