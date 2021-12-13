package ru.otus.homework13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework13.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}