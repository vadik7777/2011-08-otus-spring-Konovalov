package ru.otus.homework9.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework9.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}