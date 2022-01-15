package ru.otus.homework17.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.homework17.domain.Genre;

@RepositoryRestResource
public interface GenreRepository extends JpaRepository<Genre, Long> {
}