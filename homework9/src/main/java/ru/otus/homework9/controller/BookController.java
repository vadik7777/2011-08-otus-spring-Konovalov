package ru.otus.homework9.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework9.domain.Book;
import ru.otus.homework9.exception.NotFoundException;
import ru.otus.homework9.service.AuthorService;
import ru.otus.homework9.service.BookService;
import ru.otus.homework9.service.GenreService;

@RequiredArgsConstructor
@Controller
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping("/")
    public String allPage(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") int id, Model model) {
        Book book = bookService.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "edit";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "add";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("id") int id) {
        try {
            bookService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
        return "redirect:/";
    }

    @PostMapping("/edit")
    public String updateBook(Book book) {
        bookService.save(book);
        return "redirect:/";
    }
}