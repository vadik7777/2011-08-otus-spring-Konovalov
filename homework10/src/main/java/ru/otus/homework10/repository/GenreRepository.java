package ru.otus.homework10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework10.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}