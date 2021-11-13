package ru.otus.homework10.rest;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework10.rest.dto.AuthorDto;
import ru.otus.homework10.rest.dto.BookDto;
import ru.otus.homework10.rest.dto.GenreDto;
import ru.otus.homework10.service.BookService;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с книгами должен")
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @DisplayName("возвращать список книг")
    @Test
    void shouldCorrectGetAll() throws Exception {
        List<BookDto> bookDtoList = List.of(new BookDto(1L, "book1",
                new AuthorDto(1L, "author1"),
                new GenreDto(1L, "genre1")));

        String json = new Gson().toJson(bookDtoList);
        given(bookService.findAll()).willReturn(bookDtoList);

        mockMvc.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json));
    }

    @DisplayName("возвращать книгу")
    @Test
    void shouldCorrectGetById() throws Exception {
        Optional<BookDto> bookDto = Optional.of(new BookDto(1L, "book1",
                new AuthorDto(1L, "author1"),
                new GenreDto(1L, "genre1")));

        String json = new Gson().toJson(bookDto.get());
        given(bookService.findById(1L)).willReturn(bookDto);

        mockMvc.perform(get("/api/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json));
    }

    @DisplayName("создавать книгу")
    @Test
    void shouldCorrectСreate() throws Exception {
        BookDto bookDto = new BookDto(1L, "book1",
                new AuthorDto(1L, "author1"),
                new GenreDto(1L, "genre1"));

        String json = new Gson().toJson(bookDto);
        given(bookService.create(bookDto)).willReturn(bookDto);

        mockMvc.perform(post("/api/book")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json));
    }

    @DisplayName("обновлять книгу")
    @Test
    void shouldCorrectUpdate() throws Exception {
        Optional<BookDto> bookDto = Optional.of(new BookDto(1L, "book1",
                new AuthorDto(1L, "author1"),
                new GenreDto(1L, "genre1")));

        String json = new Gson().toJson(bookDto.get());
        given(bookService.update(1L, bookDto.get())).willReturn(bookDto);

        mockMvc.perform(put("/api/book/1")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json));
    }

    @DisplayName("удалять книгу")
    @Test
    void shouldCorrectDelete() throws Exception {
        mockMvc.perform(delete("/api/book/1"))
                .andExpect(status().isOk());
    }
}