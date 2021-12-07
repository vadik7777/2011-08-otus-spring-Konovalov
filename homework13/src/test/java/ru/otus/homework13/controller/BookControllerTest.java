package ru.otus.homework13.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework13.dto.AuthorDto;
import ru.otus.homework13.dto.BookDto;
import ru.otus.homework13.dto.GenreDto;
import ru.otus.homework13.service.AuthorService;
import ru.otus.homework13.service.BookService;
import ru.otus.homework13.service.GenreService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с книгами должен")
@WithMockUser(authorities = {"ROLE_ADMIN", "ROLE_USER"})
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

    @MockBean
    private UserDetailsService userDetailsService;

    @DisplayName("возвращать страницу с книгами")
    @Test
    void shouldCorrectGetListPage() throws Exception {
        given(bookService.findAll()).willReturn(
                List.of(new BookDto(1L, "book1",
                        new AuthorDto(1L, "author1"),
                        new GenreDto(1L, "genre1"))));
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"))
                .andExpect(content().string(containsString("book1")));
    }

    @WithMockUser(authorities = "ROLE_UNKNOWN")
    @DisplayName("делать редирект на страницу ошибки при запросе страницы с книгами")
    @Test
    void shouldInCorrectGetListPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("возвращать страницу для редактирования книги")
    @Test
    void shouldCorrectGetEditPage() throws Exception {
        given(bookService.findById(1L)).willReturn(
                Optional.of(new BookDto(1L, "book1",
                        new AuthorDto(1L, "author1"),
                        new GenreDto(1L, "genre1"))));
        mockMvc.perform(get("/edit?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(content().string(containsString("book1")));
    }

    @WithMockUser(authorities = "ROLE_UNKNOWN")
    @DisplayName("делать редирект на страницу ошибки при запросе страницы для редактирования книги")
    @Test
    void shouldInCorrectGetEditPage() throws Exception {
        mockMvc.perform(get("/edit?id=1"))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("возвращать страницу для добавления книги")
    @Test
    void shouldCorrectGetAddPage() throws Exception {
        mockMvc.perform(get("/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add"))
                .andExpect(content().string(containsString("Add book")));
    }

    @WithMockUser(authorities = "ROLE_UNKNOWN")
    @DisplayName("делать редирект на страницу ошибки при запросе страницы для добавления книги")
    @Test
    void shouldInCorrectGetAddPage() throws Exception {
        mockMvc.perform(get("/add"))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("делать редирект при удалении книги")
    @Test
    void shouldCorrectDeleteBook() throws Exception {
        mockMvc.perform(post("/delete?id=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @WithMockUser(authorities = "ROLE_UNKNOWN")
    @DisplayName("делать редирект на страницу ошибки при удалении книги")
    @Test
    void shouldInCorrectDeleteBook() throws Exception {
        mockMvc.perform(post("/delete?id=1"))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("делать редирект при создании книги")
    @Test
    void shouldCorrectCreateBook() throws Exception {
        mockMvc.perform(post("/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @WithMockUser(authorities = "ROLE_UNKNOWN")
    @DisplayName("делать редирект на страницу ошибки при создании книги")
    @Test
    void shouldInCorrectCreateBook() throws Exception {
        mockMvc.perform(post("/create"))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("делать редирект при редактировании книги")
    @Test
    void shouldCorrectUpdateBook() throws Exception {
        mockMvc.perform(post("/update?id=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @WithMockUser(authorities = "ROLE_UNKNOWN")
    @DisplayName("делать редирект на страницу ошибки при редактировании книги")
    @Test
    void shouldInCorrectUpdateBook() throws Exception {
        mockMvc.perform(post("/update?id=1"))
                .andExpect(status().is4xxClientError());
    }
}