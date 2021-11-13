package ru.otus.homework10.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookDto {
    private long id;
    private String name;
    private AuthorDto author;
    private GenreDto genre;
}