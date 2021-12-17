package ru.otus.homework14.config;

import org.junit.jupiter.api.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.homework14.domain.AuthorMongo;
import ru.otus.homework14.domain.BookMongo;
import ru.otus.homework14.domain.CommentMongo;
import ru.otus.homework14.domain.GenreMongo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.otus.homework14.config.JobConfig.MIGRATION_JOB_NAME;

@DisplayName("Миграция данных должна")
@SpringBootTest
@SpringBatchTest
class MigrationJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @DisplayName("запуститься и выполниться job-a")
    @Test
    @Order(0)
    void shouldCorrectExecute() throws Exception {
        var job = jobLauncherTestUtils.getJob();
        var jobExecution = jobLauncherTestUtils.launchJob();

        assertAll(
                () -> assertThat(job).isNotNull().extracting(Job::getName).isEqualTo(MIGRATION_JOB_NAME),
                () -> assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED")
        );
    }

    @DisplayName("корректно мигрировать автора")
    @Test
    void shouldCorrectMigrationAuthor() {
        var expectedAuthorName = "author1";
        var query = new Query();
        query.addCriteria(Criteria.where("name").is(expectedAuthorName));
        var authorMongo = mongoTemplate.findOne(query, AuthorMongo.class);

        assertThat(authorMongo).isNotNull();
    }

    @DisplayName("корректно мигрировать жанр")
    @Test
    void shouldCorrectMigrationGenre() {
        var expectedGenreName = "genre1";
        var query = new Query();
        query.addCriteria(Criteria.where("name").is(expectedGenreName));
        var genreMongo = mongoTemplate.findOne(query, GenreMongo.class);

        assertThat(genreMongo).isNotNull();
    }

    @DisplayName("корректно мигрировать книгу")
    @Test
    void shouldCorrectMigrationBook() {
        var expectedAuthorName = "author1";
        var expectedGenreName = "genre1";
        var expectedBookName = "book1";

        var query = new Query();
        query.addCriteria(Criteria.where("name").is(expectedAuthorName));
        var authorMongo = mongoTemplate.findOne(query, AuthorMongo.class);

        query = new Query();
        query.addCriteria(Criteria.where("name").is(expectedGenreName));
        var genreMongo = mongoTemplate.findOne(query, GenreMongo.class);

        query = new Query();
        query.addCriteria(Criteria.where("name").is(expectedBookName));
        var bookMongo = mongoTemplate.findOne(query, BookMongo.class);

        assertAll(
                () -> assertThat(bookMongo).isNotNull(),
                () -> assertThat(bookMongo.getAuthor()).usingRecursiveComparison().isEqualTo(authorMongo),
                () -> assertThat(bookMongo.getGenre()).usingRecursiveComparison().isEqualTo(genreMongo)
        );
    }

    @DisplayName("корректно мигрировать комментарий")
    @Test
    void shouldCorrectMigrationComment() {
        var expectedBookName = "book1";
        var expectedCommentName = "comment2";

        var query = new Query();

        query.addCriteria(Criteria.where("name").is(expectedBookName));
        var bookMongo = mongoTemplate.findOne(query, BookMongo.class);

        query = new Query();
        query.addCriteria(Criteria.where("comment").is(expectedCommentName));
        var commentMongo = mongoTemplate.findOne(query, CommentMongo.class);

        assertAll(
                () -> assertThat(commentMongo).isNotNull(),
                () -> assertThat(commentMongo.getBook()).usingRecursiveComparison().isEqualTo(bookMongo)
        );
    }
}