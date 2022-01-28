package ru.otus.homework18.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework18.dto.BookDto;
import ru.otus.homework18.exception.NotFoundException;
import ru.otus.homework18.service.AuthorService;
import ru.otus.homework18.service.BookService;
import ru.otus.homework18.service.GenreService;

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
    public String editPage(@RequestParam("id") long id, Model model) {
        BookDto bookDto = bookService.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("book", bookDto);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "edit";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        BookDto bookDto = new BookDto();
        model.addAttribute("book", bookDto);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "add";
    }

    @PostMapping("/delete")
    public String deleteBook(BookDto bookDto) {
        try {
            bookService.delete(bookDto);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
        return "redirect:/";
    }

    @PostMapping("/create")
    public String createBook(BookDto bookDto) {
        bookService.create(bookDto);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateBook(@RequestParam("id") long id, BookDto bookDto) {
        bookService.update(id, bookDto);
        return "redirect:/";
    }
}