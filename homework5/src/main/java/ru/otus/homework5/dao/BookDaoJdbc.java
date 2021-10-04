package ru.otus.homework5.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework5.dao.ext.BookResultSetExtractor;
import ru.otus.homework5.domain.Author;
import ru.otus.homework5.domain.Book;
import ru.otus.homework5.domain.Genre;
import ru.otus.homework5.exception.DuplicateException;
import ru.otus.homework5.exception.NotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Book insert(Book book) {
        if (isDuplicateBook(book)) {
            throw new DuplicateException("Duplicate book: " + book);
        }
        KeyHolder kh = new GeneratedKeyHolder();
        namedParameterJdbcOperations
                .update("insert into books (name, author_id, genre_id) values (:name, :authorId, :genreId)",
                        new MapSqlParameterSource()
                                .addValue("name", book.getName())
                                .addValue("authorId", book.getAuthor().getId())
                                .addValue("genreId", book.getGenre().getId()),
                        kh);
        book.setId(kh.getKey().longValue());
        return book;
    }

    private boolean isDuplicateBook(Book book) {
        return namedParameterJdbcOperations.queryForObject("select exists(select id from books where name = :name " +
                        "and author_id = :authorId and genre_id = :genreId)",
                Map.of("name", book.getName(), "authorId", book.getAuthor().getId(),
                        "genreId", book.getGenre().getId()), Boolean.class);
    }

    @Override
    public Book update(Book book) {
        if (book.getId() == 0 || !isExistsBookById(book.getId())) {
            throw new NotFoundException("book not found id: " + book.getId());
        }
        namedParameterJdbcOperations
                .update("update books set name = :name, author_id = :authorId, genre_id = :genreId " +
                                "where id = :id",
                        Map.of("id", book.getId(), "name", book.getName(),
                                "authorId", book.getAuthor().getId(),
                                "genreId", book.getGenre().getId()));
        return book;
    }

    private boolean isExistsBookById(long id) {
        try {
            return namedParameterJdbcOperations.queryForObject("select exists (select id from books where id = :id)",
                    Map.of("id", id), Boolean.class);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        List<Book> books = namedParameterJdbcOperations.query(
                "select b.id, b.name as book_name, b.author_id, b.genre_id," +
                        " a.id, a.name as author_name, g.id, g.name as genre_name " +
                        "from (books b left join authors a on b.author_id = a.id) " +
                        "left join genres g on b.genre_id = g.id where b.id = :id",
                params, new BookMapper());
        if (books.isEmpty()) {
            throw new NotFoundException("book not found id: " + id);
        }
        return books.get(0);
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = namedParameterJdbcOperations.query(
                "select b.id, b.name as book_name, b.author_id, b.genre_id," +
                        " a.id, a.name as author_name, g.id, g.name as genre_name " +
                        "from (books b left join authors a on b.author_id = a.id) " +
                        "left join genres g on b.genre_id = g.id", new BookResultSetExtractor());
        return books;
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbcOperations.update("delete from books where id = :id", Map.of("id", id));
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int i) throws SQLException {
            Book book = Book.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .genre(Genre.builder()
                            .id(rs.getLong("genre_id"))
                            .name(rs.getString("genre_name"))
                            .build())
                    .author(Author.builder()
                            .id(rs.getLong("author_id"))
                            .name(rs.getString("author_name"))
                            .build())
                    .build();
            return book;
        }
    }
}