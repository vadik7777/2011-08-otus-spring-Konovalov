package ru.otus.homework12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework12.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}