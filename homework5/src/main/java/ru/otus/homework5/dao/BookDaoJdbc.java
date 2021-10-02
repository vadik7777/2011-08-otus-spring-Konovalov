package ru.otus.homework5.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework5.dao.ext.BookResultSetExtractor;import ru.otus.homework5.domain.Book;
import ru.otus.homework5.exception.DuplicateBookException;
import ru.otus.homework5.exception.BookNotFoundException;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Book insert(Book book) {

        KeyHolder kh = new GeneratedKeyHolder();

        book.getGenre().setId(getGenreIdByName(book.getGenre().getName(), kh));
        book.getAuthor().setId(getAuthorIdByName(book.getAuthor().getName(), kh));

        try {
            namedParameterJdbcOperations.queryForObject("select id from books where name = :name " +
                                    "and author_id = :authorId and genre_id = :genreId",
                    Map.of("name", book.getName(), "authorId", book.getAuthor().getId(),
                            "genreId", book.getGenre().getId()), Long.class);
            throw new DuplicateBookException("Duplicate book: " + book.toString());
        } catch (EmptyResultDataAccessException e) {
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
    }

    private long getGenreIdByName(String name, KeyHolder kh) {
        try {
            return namedParameterJdbcOperations.queryForObject("select id from genres where name = :name",
                    Map.of("name", name), Long.class);
        } catch (EmptyResultDataAccessException e) {
            namedParameterJdbcOperations.update("insert into genres (name) values (:name)",
                    new MapSqlParameterSource().addValue("name", name), kh);
            return kh.getKey().longValue();
        }
    }

    private long getAuthorIdByName(String name, KeyHolder kh) {
        try {
            return namedParameterJdbcOperations.queryForObject("select id from authors where name = :name",
                    Map.of("name", name), Long.class);
        } catch (EmptyResultDataAccessException e) {
            namedParameterJdbcOperations.update("insert into authors (name) values (:name)",
                    new MapSqlParameterSource().addValue("name", name), kh);
            return kh.getKey().longValue();
        }
    }

    @Override
    public Book update(Book book) {
        if (book.getId() == 0 || !isExistsBookById(book.getId())) {
            throw new BookNotFoundException("book not found id: " + book.getId());
        }

        KeyHolder kh = new GeneratedKeyHolder();

        book.getGenre().setId(getGenreIdByName(book.getGenre().getName(), kh));
        book.getAuthor().setId(getAuthorIdByName(book.getAuthor().getName(), kh));

        namedParameterJdbcOperations
                .update("update books set name = :name, author_id = :authorId, genre_id = :genreId " +
                                "where id = :id",
                        Map.of("id", book.getId(),"name", book.getName(),
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
                params, new BookResultSetExtractor());
        if (books.isEmpty()) {
            throw new BookNotFoundException("book not found id: " + id);
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
}