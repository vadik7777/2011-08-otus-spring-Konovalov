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
import ru.otus.homework11.domain.Genre;
import ru.otus.homework11.mapper.GenreMapper;
import ru.otus.homework11.repository.mongoreactive.GenreReactiveRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@DisplayName("Контроллер для работы с жанрами должен")
class GenreControllerTest {

    @Autowired
    private GenreMapper genreMapper;

    @MockBean
    private GenreReactiveRepository genreReactiveRepository;

    @Autowired
    private GenreController genreController;

    private WebTestClient client;

    @PostConstruct
    private void init() {
        client = WebTestClient.bindToController(genreController).build();
    }

    @DisplayName("возвращать список жанров")
    @Test
    void shouldCorrectGetAll() {
        val genreList = List.of(
                new Genre("id_genre1", "genre1"),
                new Genre("id_genre2", "genre2"),
                new Genre("id_genre3", "genre3")
        );

        val json = new Gson().toJson(genreList.stream().map(genreMapper::toDto).collect(Collectors.toList()));
        given(genreReactiveRepository.findAll()).willReturn(Flux.fromIterable(genreList));

        client.get().uri("/api/genre")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(json);
    }
}