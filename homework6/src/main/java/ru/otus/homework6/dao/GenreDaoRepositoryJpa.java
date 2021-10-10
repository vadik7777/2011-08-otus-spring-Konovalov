package ru.otus.homework6.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework6.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreDaoRepositoryJpa implements GenreDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Genre insert(Genre genre) {
        entityManager.persist(genre);
        return genre;
    }

    @Override
    public Genre update(Genre genre) {
        return entityManager.merge(genre);
    }

    @Override
    public Optional<Genre> getById(long id) {
        return Optional.ofNullable(entityManager.find(Genre.class, id));
    }
}