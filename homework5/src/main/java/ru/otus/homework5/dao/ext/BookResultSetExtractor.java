package ru.otus.homework5.dao.ext;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.homework5.domain.Author;
import ru.otus.homework5.domain.Book;
import ru.otus.homework5.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookResultSetExtractor implements ResultSetExtractor<List<Book>> {
    @Override
    public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Book> books = new ArrayList<>();
        while (rs.next()) {
            Book book = Book.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .genre(Genre.builder()
                            .id(rs.getLong("genre_id"))
                            .name(rs.getNString("genre_name"))
                            .build())
                    .author(Author.builder()
                            .id(rs.getLong("author_id"))
                            .name(rs.getString("author_name"))
                            .build())
                    .build();
            books.add(book);
        }
        return books;
    }
}