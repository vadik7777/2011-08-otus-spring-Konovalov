package ru.otus.homework7.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework7.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}