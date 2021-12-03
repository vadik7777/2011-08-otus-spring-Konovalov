package ru.otus.homework12.dto;

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
}