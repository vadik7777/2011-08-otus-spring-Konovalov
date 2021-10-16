package ru.otus.homework7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework7.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}