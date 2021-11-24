package ru.otus.homework11.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework11.domain.Book;
import ru.otus.homework11.mapper.BookMapper;
import ru.otus.homework11.repository.mongoreactive.AuthorReactiveRepository;
import ru.otus.homework11.repository.mongoreactive.BookReactiveRepository;
import ru.otus.homework11.repository.mongoreactive.GenreReactiveRepository;
import ru.otus.homework11.rest.dto.BookDto;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookReactiveRepository bookReactiveRepository;
    private final AuthorReactiveRepository authorReactiveRepository;
    private final GenreReactiveRepository genreReactiveRepository;
    private final BookMapper bookMapper;

    @GetMapping("api/book")
    public Flux<BookDto> getAll() {
        return bookReactiveRepository.findAll().map(bookMapper::toDto);
    }

    @GetMapping("api/book/{id}")
    public Mono<ResponseEntity<BookDto>> getById(@PathVariable String id) {
        return bookReactiveRepository.findById(id)
                .map(book -> ResponseEntity.ok(bookMapper.toDto(book)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("api/book")
    public Mono<ResponseEntity<BookDto>> create(@RequestBody BookDto bookDto) {
        return Mono.zip(authorReactiveRepository.findById(bookDto.getAuthor().getId()),
                genreReactiveRepository.findById(bookDto.getGenre().getId()))
                .flatMap(zip -> {
                    Book book = bookMapper.toEntity(bookDto);
                    book.setAuthor(zip.getT1());
                    book.setGenre(zip.getT2());
                    return bookReactiveRepository.save(book);
                })
                .map(book -> ResponseEntity.status(HttpStatus.CREATED).body(bookMapper.toDto(book)))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PutMapping("api/book/{id}")
    public Mono<ResponseEntity<BookDto>> update(@PathVariable String id, @RequestBody BookDto bookDto) {
        return Mono.zip(authorReactiveRepository.findById(bookDto.getAuthor().getId()),
                genreReactiveRepository.findById(bookDto.getGenre().getId()),
                bookReactiveRepository.findById(id))
                .flatMap(zip -> {
                    Book updateBook = bookMapper.toEntity(bookDto);
                    updateBook.setAuthor(zip.getT1());
                    updateBook.setGenre(zip.getT2());
                    Book book = zip.getT3();
                    BeanUtils.copyProperties(updateBook, book, "id");
                    return bookReactiveRepository.save(book);
                })
                .map(book -> ResponseEntity.ok().body(bookMapper.toDto(book)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("api/book/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return bookReactiveRepository.deleteById(id)
                .map(empty -> ResponseEntity.accepted().body(empty));
    }
}