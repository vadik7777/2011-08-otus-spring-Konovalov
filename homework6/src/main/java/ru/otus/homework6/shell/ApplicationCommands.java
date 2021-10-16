package ru.otus.homework6.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework6.domain.Author;
import ru.otus.homework6.domain.Book;
import ru.otus.homework6.domain.Comment;
import ru.otus.homework6.domain.Genre;
import ru.otus.homework6.service.BookService;
import ru.otus.homework6.service.CommentService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {

    private final BookService bookService;

    @ShellMethod(value = "Insert book - enter: book name, author name, genre name, comment content",
            key = {"i", "insert"})
    public String insertBook(@ShellOption(defaultValue = "book name") String bookName,
                             @ShellOption(defaultValue = "author name") String authorName,
                             @ShellOption(defaultValue = "genre name") String genreName,
                             @ShellOption(defaultValue = "comment content") String commentContent) {
        Author author = new Author(0, authorName);
        Genre genre = new Genre(0, genreName);
        Comment comment = new Comment(0, commentContent, null);
        Book book = new Book(0, bookName, author, genre, Collections.singletonList(comment));
        comment.setBook(book);
        bookService.insert(book);
        return "insert book successful: " + book;
    }

    @ShellMethod(value = "Get book by id - enter: book id", key = {"g", "get"})
    public String getBookById(@ShellOption(defaultValue = "1") long bookId) {
        Optional<Book> book = bookService.getById(bookId);
        Book bookToSting = book.orElseThrow(() -> new RuntimeException("book not found id:" + bookId));
        return "get book by id successful: " + bookToSting.getId() + ", " + bookToSting.getName() + ", "
                + bookToSting.getAuthor() + ", " + bookToSting.getGenre();
    }

    @ShellMethod(value = "Get all books", key = {"a", "all"})
    public String getAllBooks() {
        List<Book> books = bookService.getAll();
        if (books.isEmpty()) {
            return "get all books successful: result is empty";
        } else {
            return "get all books successful: \n" +
                    books.stream().map(b -> b.getId() + ",  " + b.getName()).collect(Collectors.joining("\n"));
        }
    }

    @ShellMethod(value = "Update book - enter: book id, book name, author name, genre name, comment content",
            key = {"u", "update"})
    public String updateBook(@ShellOption(defaultValue = "1") long bookId,
                             @ShellOption(defaultValue = "book name") String bookName,
                             @ShellOption(defaultValue = "author name") String authorName,
                             @ShellOption(defaultValue = "genre name") String genreName) {
        Book book = bookService.getById(bookId)
                .orElseThrow(() -> new RuntimeException("book not found id:" + bookId));
        book.setName(bookName);
        book.setAuthor(new Author(0, authorName));
        book.setGenre(new Genre(0, genreName));
        bookService.update(book);
        return "update book successful: " +  book.getId() + ", " + book.getName() + ", "
                + book.getAuthor() + ", " + book.getGenre();
    }

    @ShellMethod(value = "Delete book by id - enter: book id", key = {"d", "delete"})
    public String deleteBookById(@ShellOption(defaultValue = "1") long bookId) {
        bookService.deleteById(bookId);
        return "delete book by id successful: " + bookId;
    }
}