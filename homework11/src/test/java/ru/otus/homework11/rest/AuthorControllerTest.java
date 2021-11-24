package ru.otus.homework11.rest;

import com.google.gson.Gson;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.homework11.domain.Author;
import ru.otus.homework11.mapper.AuthorMapper;
import ru.otus.homework11.repository.mongoreactive.AuthorReactiveRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@DisplayName("Контроллер для работы с авторами должен")
class AuthorControllerTest {

    @Autowired
    private AuthorMapper authorMapper;

    @MockBean
    private AuthorReactiveRepository authorReactiveRepository;

    @Autowired
    private AuthorController authorController;

    private WebTestClient client;

    @PostConstruct
    private void init() {
        client = WebTestClient.bindToController(authorController).build();
    }

    @DisplayName("возвращать список авторов")
    @Test
    void shouldCorrectGetAll() {
        val authorList = List.of(
                new Author("id_author1", "author1"),
                new Author("id_author2", "author2"),
                new Author("id_author3", "author3")
        );

        val json = new Gson().toJson(authorList.stream().map(authorMapper::toDto).collect(Collectors.toList()));
        given(authorReactiveRepository.findAll()).willReturn(Flux.fromIterable(authorList));

        client.get().uri("/api/author")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(json);
    }
}