package ru.otus.homework5.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework5.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Genre insertWithOutId(Genre genre) {
        KeyHolder kh = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update("insert into genres (name) values (:name)",
                new MapSqlParameterSource().addValue("name", genre.getName()), kh);
        genre.setId(kh.getKey().longValue());
        return genre;
    }

    @Override
    public Genre getById(long id) {
        return namedParameterJdbcOperations.queryForObject("select id, name from genres where id = :id",
                Map.of("id", id), new GenreMapper());
    }

    @Override
    public Genre getByName(String name) {
        return namedParameterJdbcOperations.queryForObject("select id, name from genres where name = :name",
                Map.of("name", name), new GenreMapper());
    }

    @Override
    public Genre getByIdAndName(long id, String name) {
        return namedParameterJdbcOperations.queryForObject("select id, name from genres " +
                        "where id = :id and name = :name",
                Map.of("id", id, "name", name), new GenreMapper());
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}