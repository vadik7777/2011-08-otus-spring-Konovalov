package ru.otus.homework10.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework10.exception.NotFoundException;
import ru.otus.homework10.rest.dto.BookDto;
import ru.otus.homework10.service.BookService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @GetMapping("api/book")
    public List<BookDto> getAll(){
        return bookService.findAll();
    }

    @GetMapping("api/book/{id}")
    public BookDto getById(@PathVariable long id){
        return bookService.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping("api/book")
    public BookDto create(@RequestBody BookDto bookDto) {
        return bookService.create(bookDto);
    }

    @PutMapping("api/book/{id}")
    public BookDto update(@PathVariable long id, @RequestBody BookDto bookDto) {
        return bookService.update(id, bookDto).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping("api/book/{id}")
    public void delete(@PathVariable long id) {
        bookService.deleteById(id);
    }
}