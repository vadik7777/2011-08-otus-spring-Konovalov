package ru.otus.homework11.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;
import ru.otus.homework11.domain.Author;
import ru.otus.homework11.repository.mongoreactive.AuthorReactiveRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@DisplayName("Репозиторий для работы с авторами должен")
class AuthorRepositoryTest {

    @Autowired
    private AuthorReactiveRepository authorReactiveRepository;

    @DisplayName("должен загружать список всех авторов")
    @Test
    void shouldCorrectFindAll() {

        val expectedAuthor = new Author("id_author1", "author1");

        val authorFlux = authorReactiveRepository.findAll();

        StepVerifier.create(authorFlux)
                .assertNext(actualAuthor -> assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor))
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }
}