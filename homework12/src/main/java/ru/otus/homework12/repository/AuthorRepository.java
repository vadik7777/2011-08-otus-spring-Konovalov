package ru.otus.homework12.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework12.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}