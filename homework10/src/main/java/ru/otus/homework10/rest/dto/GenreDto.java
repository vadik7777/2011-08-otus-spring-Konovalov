package ru.otus.homework10.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GenreDto {
    private long id;
    private String name;
}