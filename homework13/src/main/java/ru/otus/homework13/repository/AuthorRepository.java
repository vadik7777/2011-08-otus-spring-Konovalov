package ru.otus.homework13.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework13.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}