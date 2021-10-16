package ru.otus.homework7.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.homework7.domain.Book;
import ru.otus.homework7.domain.Comment;

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
        val exceptedComment = new Comment(0, "exceptedComment", book);
        commenRepository.save(exceptedComment);
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
        commenRepository.save(exceptedComment);
        val actualComment = testEntityManager.find(Comment.class, 1L);
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(exceptedComment);
    }

    @DisplayName("получать комментарий по id")
    @Test
    void shouldCorrectFindById() {
        val exceptedComment = testEntityManager.find(Comment.class, 1L);
        testEntityManager.detach(exceptedComment);
        val actualComment = commenRepository.findById(1L);
        assertThat(actualComment).isPresent().get().usingRecursiveComparison().isEqualTo(exceptedComment);
    }

    @DisplayName("должен загружать список всех комментариев")
    @Test
    void shouldCorrectFindAll() {
        val comments = commenRepository.findAll();
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
        val actualComments =  commenRepository.findCommentByBookId(1L);
        assertThat(actualComments).usingRecursiveComparison().isEqualTo(expectedComments);
    }
}