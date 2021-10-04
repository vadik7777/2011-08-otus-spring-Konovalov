package ru.otus.homework5.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework5.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Author insertWithOutId(Author author) {
        KeyHolder kh = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update("insert into authors (name) values (:name)",
                new MapSqlParameterSource().addValue("name", author.getName()), kh);
        author.setId(kh.getKey().longValue());
        return author;
    }

    @Override
    public Author getById(long id) {
        return namedParameterJdbcOperations.queryForObject("select id, name from authors where id = :id",
                Map.of("id", id), new AuthorMapper());
    }

    @Override
    public Author getByName(String name) {
        return namedParameterJdbcOperations.queryForObject("select id, name from authors where name = :name",
                Map.of("name", name), new AuthorMapper());
    }

    @Override
    public Author getByIdAndName(long id, String name) {
        return namedParameterJdbcOperations.queryForObject("select id, name from authors " +
                        "where id = :id and name = :name",
                Map.of("id", id, "name", name), new AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }
}