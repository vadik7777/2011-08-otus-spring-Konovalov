package ru.otus.homework6.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework6.domain.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookDaoRepositoryJpa implements BookDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Book insert(Book book) {
        entityManager.persist(book);
        return book;
    }

    @Override
    public Book update(Book book) {
        return entityManager.merge(book);
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public List<Book> getAll() {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-entity-graph");
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = entityManager.createQuery("delete from Book c where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}