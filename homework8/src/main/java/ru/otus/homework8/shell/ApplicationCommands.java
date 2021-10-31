package ru.otus.homework8.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework8.domain.Author;
import ru.otus.homework8.domain.Book;
import ru.otus.homework8.domain.Comment;
import ru.otus.homework8.domain.Genre;
import ru.otus.homework8.service.AuthorService;
import ru.otus.homework8.service.BookService;
import ru.otus.homework8.service.CommentService;
import ru.otus.homework8.service.GenreService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {

    private final BookService bookService;
    private final CommentService commentService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @ShellMethod(value = "Insert book - enter: book name, author name, genre name",
            key = {"i", "insert"})
    public String insertBook(@ShellOption(defaultValue = "book name") String bookName,
                             @ShellOption(defaultValue = "author name") String authorName,
                             @ShellOption(defaultValue = "genre name") String genreName) {
        Author author = new Author(authorName);
        authorService.save(author);
        Genre genre = new Genre(genreName);
        genreService.save(genre);
        Book book = new Book(bookName, author, genre);
        bookService.save(book);
        return "insert book successful: " + book;
    }

    @ShellMethod(value = "Get book by id - enter: book id", key = {"g", "get"})
    public String getBookById(@ShellOption(defaultValue = "id_book1") String bookId) {
        Optional<Book> book = bookService.findById(bookId);
        return "get book by id successful: " + book.orElseThrow(() -> new RuntimeException("book not found id:" + bookId));
    }

    @ShellMethod(value = "Get all books", key = {"a", "all"})
    public String getAllBooks() {
        List<Book> books = bookService.findAll();
        if (books.isEmpty()) {
            return "get all books successful: result is empty";
        } else {
            return "get all books successful: \n" +
                    books.stream().map(Book::toString).collect(Collectors.joining("\n"));
        }
    }

    @ShellMethod(value = "Update book - enter: book id, book name, author name, genre name",
            key = {"u", "update"})
    public String updateBook(@ShellOption(defaultValue = "id_book1") String bookId,
                             @ShellOption(defaultValue = "book name") String bookName,
                             @ShellOption(defaultValue = "author name") String authorName,
                             @ShellOption(defaultValue = "genre name") String genreName) {
        Book book = bookService.findById(bookId)
                .orElseThrow(() -> new RuntimeException("book not found id:" + bookId));
        Author author = new Author(authorName);
        authorService.save(author);
        Genre genre = new Genre(genreName);
        genreService.save(genre);
        book.setName(bookName);
        book.setAuthor(author);
        book.setGenre(genre);
        bookService.save(book);
        return "update book successful: " +  book.getId() + ", " + book.getName() + ", "
                + book.getAuthor() + ", " + book.getGenre();
    }

    @ShellMethod(value = "Delete book by id - enter: book id", key = {"d", "delete"})
    public String deleteBookById(@ShellOption(defaultValue = "id_book1") String bookId) {
        bookService.deleteById(bookId);
        return "delete book by id successful: " + bookId;
    }

    @ShellMethod(value = "Find comments by book id - enter book id", key = {"c", "comments"})
    public String findCommentByBook(@ShellOption(defaultValue = "id_book1") String bookId) {
        List<Comment> comments = commentService.findCommentByBookId(bookId);
        if (comments.isEmpty()) {
            return "comments for book is empty";
        } else {
            return "comments for book: \n" +
                    comments.stream().map(Comment::toString).collect(Collectors.joining("\n"));
        }
    }
}