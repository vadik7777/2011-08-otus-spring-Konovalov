package ru.otus.homework9.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework9.domain.Author;
import ru.otus.homework9.domain.Book;
import ru.otus.homework9.domain.Genre;
import ru.otus.homework9.service.AuthorService;
import ru.otus.homework9.service.BookService;
import ru.otus.homework9.service.GenreService;

import java.util.List;
import java.util.Optional;


import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с книгами должен")
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @DisplayName("возвращать страницу с книгами")
    @Test
    void shouldCorrectGetListPage() throws Exception {
        given(bookService.findAll()).willReturn(
                List.of(new Book(1L, "book1",
                        new Author(1L, "author1"),
                        new Genre(1L, "genre1"))));
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"))
                .andExpect(content().string(containsString("book1")));
    }

    @DisplayName("возвращать страницу для редактирования книги")
    @Test
    void shouldCorrectGetEditPage() throws Exception {
        given(bookService.findById(1L)).willReturn(
                Optional.of(new Book(1L, "book1",
                        new Author(1L, "author1"),
                        new Genre(1L, "genre1"))));
        mockMvc.perform(get("/edit?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(content().string(containsString("book1")));
    }

    @DisplayName("возвращать страницу для добавления книги")
    @Test
    void shouldCorrectGetAddPage() throws Exception {
        mockMvc.perform(get("/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add"))
                .andExpect(content().string(containsString("Add book")));
    }

    @DisplayName("делать редирект при удалении книги")
    @Test
    void shouldCorrectDeleteBook() throws Exception {
        mockMvc.perform(get("/delete?id=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @DisplayName("делать редирект на страницу ошибки при удалении книги")
    @Test
    void shouldInCorrectDeleteBook() throws Exception {
        doThrow(new EmptyResultDataAccessException(1)).when(bookService).deleteById(10L);
        mockMvc.perform(get("/delete?id=10"))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("делать редирект при создании или редактировании книги")
    @Test
    void updateBook() throws Exception {
        mockMvc.perform(post("/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}