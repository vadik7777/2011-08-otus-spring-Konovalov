package ru.otus.homework18.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.homework18.domain.Author;

@RepositoryRestResource
public interface AuthorRepository extends JpaRepository<Author, Long> {
}