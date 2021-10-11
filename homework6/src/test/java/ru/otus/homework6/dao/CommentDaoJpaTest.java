package ru.otus.homework6.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework6.domain.Book;
import ru.otus.homework6.domain.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с комментариями должно")
@DataJpaTest
@Import(CommentDaoJpa.class)
class CommentDaoJpaTest {

    @Autowired
    private CommentDaoJpa commentDaoJpa;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("добавлять комментарий")
    @Test
    void shouldCorrectInsert() {
        val book = testEntityManager.find(Book.class, 1L);
        val exceptedComment = new Comment(0, "exceptedComment", book);
        commentDaoJpa.insert(exceptedComment);
        testEntityManager.detach(exceptedComment);
        val actualComment = testEntityManager.find(Comment.class, exceptedComment.getId());
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(exceptedComment);
    }

    @DisplayName("обновлять комментарий")
    @Test
    void shouldCorrectUpdate() {
        val exceptedComment = testEntityManager.find(Comment.class, 1L);
        exceptedComment.setComment("newComment");
        testEntityManager.detach(exceptedComment);
        commentDaoJpa.update(exceptedComment);
        val actualComment = testEntityManager.find(Comment.class, 1L);
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(exceptedComment);
    }

    @DisplayName("получать комментарий по id")
    @Test
    void shouldCorrectGetById() {
        val exceptedComment = testEntityManager.find(Comment.class, 1L);
        testEntityManager.detach(exceptedComment);
        val actualComment = commentDaoJpa.getById(1L);
        assertThat(actualComment).isPresent().get().usingRecursiveComparison().isEqualTo(exceptedComment);
    }

    @DisplayName("должен загружать список всех комментариев")
    @Test
    void shouldCorrectGetAll() {
        val comments = commentDaoJpa.getAll();
        assertThat(comments).isNotNull().hasSize(4)
                .allMatch(c -> !c.getComment().equals(""))
                .allMatch(c -> c.getBook() != null)
                .allMatch(c -> c.getBook().getAuthor() != null)
                .allMatch(c -> c.getBook().getGenre() != null);
    }

    @DisplayName("удалять комментарий по id")
    @Test
    void shouldCorrectDeleteById() {
        val deleteComment = testEntityManager.find(Comment.class, 1L);
        assertThat(deleteComment).isNotNull();
        commentDaoJpa.deleteById(1L);
        testEntityManager.detach(deleteComment);
        val deletedComment = testEntityManager.find(Comment.class, 1L);
        assertThat(deletedComment).isNull();
    }
}