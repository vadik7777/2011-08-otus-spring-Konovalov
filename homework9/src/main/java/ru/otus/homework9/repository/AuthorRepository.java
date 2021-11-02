package ru.otus.homework9.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework9.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}