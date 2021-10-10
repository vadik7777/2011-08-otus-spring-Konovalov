package ru.otus.homework5.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework5.domain.Author;
import ru.otus.homework5.domain.Book;
import ru.otus.homework5.domain.Genre;
import ru.otus.homework5.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {

    private final BookService bookService;

    @ShellMethod(value = "Insert book - enter: book name, author name, genre name", key = {"i", "insert"})
    public String insertBook(@ShellOption(defaultValue = "book name") String bookName,
                             @ShellOption(defaultValue = "author name") String authorName,
                             @ShellOption(defaultValue = "genre name") String genreName) {
        Book book = new Book().builder()
                .name(bookName)
                .author(Author.builder().name(authorName).build())
                .genre(Genre.builder().name(genreName).build())
                .build();
        bookService.insert(book);
        return "insert book successful: " + book;
    }

    @ShellMethod(value = "Get book by id - enter: book id", key = {"g", "get"})
    public String getBookById(@ShellOption(defaultValue = "1") long bookId) {
        Book book = bookService.getById(bookId);
        return "get book by id successful: " + book;
    }

    @ShellMethod(value = "Get all books", key = {"a", "all"})
    public String getAllBooks() {
        List<Book> books = bookService.getAll();
        if (books.isEmpty()) {
            return "get all books successful: result is empty";
        } else {
            return "get all books successful: \n" +
                    books.stream().map(Book::toString).collect(Collectors.joining("\n"));
        }
    }

    @ShellMethod(value = "Update book - enter: book id, book name, author name, genre name", key = {"u", "update"})
    public String updateBook(@ShellOption(defaultValue = "1") long bookId,
                             @ShellOption(defaultValue = "book name") String bookName,
                             @ShellOption(defaultValue = "author name") String authorName,
                             @ShellOption(defaultValue = "genre name") String genreName) {
        Book book = new Book().builder()
                .id(bookId)
                .name(bookName)
                .author(Author.builder().name(authorName).build())
                .genre(Genre.builder().name(genreName).build())
                .build();
        bookService.update(book);
        return "update book successful: " + book;
    }

    @ShellMethod(value = "Delete book by id - enter: book id", key = {"d", "delete"})
    public String deleteBookById(@ShellOption(defaultValue = "1") long bookId) {
        bookService.deleteById(bookId);
        return "delete book by id successful: " + bookId;
    }
}