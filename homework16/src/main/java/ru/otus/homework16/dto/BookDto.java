package ru.otus.homework16.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {
    private long id;
    private String name;
    private AuthorDto author;
    private GenreDto genre;

    public BookDto(String name, AuthorDto author, GenreDto genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }
}