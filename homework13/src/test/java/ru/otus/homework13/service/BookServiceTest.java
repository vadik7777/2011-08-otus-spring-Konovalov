package ru.otus.homework13.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.test.context.support.WithMockUser;
import ru.otus.homework13.dto.AuthorDto;
import ru.otus.homework13.dto.BookDto;
import ru.otus.homework13.dto.GenreDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Сервис для работы с книгами должен")
@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private AclService aclService;

    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName("обновлять книгу для корректной роли")
    @Test
    void shouldCorrectUpdate() {
        AuthorDto expectedAuthorDto = new AuthorDto(1L, "fio1");
        GenreDto expectedGenreDto = new GenreDto(1L, "genre1");
        BookDto expectedBookDto = new BookDto(1L, "book1", expectedAuthorDto, expectedGenreDto);
        assertThat(bookService.update(1L, expectedBookDto).orElseThrow())
                .usingRecursiveComparison().isEqualTo(expectedBookDto);
    }

    @WithMockUser(authorities = {"ROLE_USER"})
    @DisplayName("вызывать ошибку при обновлени книги для не корректной роли")
    @Test
    void shouldInCorrectUpdate() {
        assertThatThrownBy(() -> bookService.update(1L, new BookDto())).isInstanceOf(AccessDeniedException.class);
    }

    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName("возвращать книгу для корректной роли")
    @Test
    void shouldCorrectFindById() {
        AuthorDto expectedAuthorDto = new AuthorDto(1L, "fio1");
        GenreDto expectedGenreDto = new GenreDto(1L, "genre1");
        BookDto expectedBookDto = new BookDto(1L, "book1", expectedAuthorDto, expectedGenreDto);
        assertThat(bookService.findById(1L).orElseThrow())
                .usingRecursiveComparison().isEqualTo(expectedBookDto);
    }

    @WithMockUser(authorities = {"ROLE_USER"})
    @DisplayName("вызывать ошибку при возвращении книги для не корректной роли")
    @Test
    void shouldInCorrectFindById() {
        assertThatThrownBy(() -> bookService.findById(1L)).isInstanceOf(AccessDeniedException.class);
    }

    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName("возвращать список книг для корректной роли")
    @Test
    void shouldCorrectFindAll() {
        List<BookDto> expectedBookDtoList = List.of(
                new BookDto(1L, "book1",
                        new AuthorDto(1L, "fio1"), new GenreDto(1L, "genre1")),
                new BookDto(2L, "book2",
                        new AuthorDto(2L, "fio2"), new GenreDto(2L, "genre2"))
        );
        assertThat(bookService.findAll())
                .usingRecursiveComparison().isEqualTo(expectedBookDtoList);
    }

    @WithMockUser(authorities = {"ROLE_USER"})
    @DisplayName("удалять книгу для корректной роли")
    @Test
    void shouldCorrectDelete() {
        BookDto bookDto = new BookDto();
        bookDto.setId(3L);
        bookService.delete(bookDto);
        assertThat(bookService.findById(3L)).isEmpty();
    }

    @WithMockUser(authorities = {"ROLE_USER"})
    @DisplayName("добавлять книгу для корректной роли")
    @Test
    void shouldCorrectCreate() {
        AuthorDto expectedAuthorDto = new AuthorDto(1L, "fio1");
        GenreDto expectedGenreDto = new GenreDto(1L, "genre1");
        BookDto expectedBookDto = new BookDto("book4", expectedAuthorDto, expectedGenreDto);
        BookDto actualBookDto = bookService.create(expectedBookDto);

        AccessControlEntry ace = aclService.readAclById(new ObjectIdentityImpl(actualBookDto)).getEntries().get(0);
        assertAll(
                () -> assertThat(actualBookDto).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedBookDto),
                () -> assertThat(ace.getSid()).isEqualTo(new GrantedAuthoritySid("ROLE_USER")),
                () -> assertThat(ace.getPermission()).isEqualTo(BasePermission.ADMINISTRATION)
        );
    }
}