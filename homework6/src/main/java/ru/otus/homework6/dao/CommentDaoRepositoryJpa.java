package ru.otus.homework6.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework6.domain.Comment;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentDaoRepositoryJpa implements CommentDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Comment insert(Comment comment) {
        entityManager.persist(comment);
        return comment;
    }

    @Override
    public Comment update(Comment comment) {
        return entityManager.merge(comment);
    }

    @Override
    public Optional<Comment> getById(long id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }

    @Override
    public List<Comment> getAll() {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("comment-entity-graph");
        TypedQuery<Comment> query = entityManager.createQuery("select c from Comment c", Comment.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = entityManager.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}