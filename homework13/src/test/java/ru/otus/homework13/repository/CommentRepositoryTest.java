package ru.otus.homework13.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.homework13.domain.Book;
import ru.otus.homework13.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с комментариями должен")
@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommenRepository commenRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("добавлять комментарий")
    @Test
    void shouldCorrectInsert() {
        val book = testEntityManager.find(Book.class, 1L);
        val expectedComment = new Comment(0, "expectedComment", book);
        commenRepository.save(expectedComment);
        testEntityManager.detach(expectedComment);
        val actualComment = testEntityManager.find(Comment.class, expectedComment.getId());
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("обновлять комментарий")
    @Test
    void shouldCorrectUpdate() {
        val expectedComment = testEntityManager.find(Comment.class, 1L);
        expectedComment.setComment("newComment");
        testEntityManager.detach(expectedComment);
        commenRepository.save(expectedComment);
        val actualComment = testEntityManager.find(Comment.class, 1L);
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("получать комментарий по id")
    @Test
    void shouldCorrectFindById() {
        val expectedComment = testEntityManager.find(Comment.class, 1L);
        testEntityManager.detach(expectedComment);
        val actualComment = commenRepository.findById(1L);
        assertThat(actualComment).isPresent().get().usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("должен загружать список всех комментариев")
    @Test
    void shouldCorrectFindAll() {
        val comments = commenRepository.findAll();
        assertThat(comments).isNotNull().hasSize(5)
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
        commenRepository.deleteById(1L);
        testEntityManager.flush();
        val deletedComment = testEntityManager.find(Comment.class, 1L);
        assertThat(deletedComment).isNull();
    }

    @DisplayName("получать комментарии по id книги")
    @Test
    void shouldCorrectFindCommentByBookId() {
        val expectedComment1 = testEntityManager.find(Comment.class, 1L);
        val expectedComment2 = testEntityManager.find(Comment.class, 2L);
        val expectedComments = List.of(expectedComment1, expectedComment2);
        testEntityManager.detach(expectedComment1);
        testEntityManager.detach(expectedComment2);
        val actualComments = commenRepository.findCommentByBookId(1L);
        assertThat(actualComments).usingRecursiveComparison().isEqualTo(expectedComments);
    }
}