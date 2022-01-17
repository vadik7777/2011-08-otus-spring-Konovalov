package ru.otus.homework17.repository;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.homework17.domain.Book;

import java.util.Optional;

@RepositoryRestResource
public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author", "genre"})
    Optional<Book> findById(long id);
}