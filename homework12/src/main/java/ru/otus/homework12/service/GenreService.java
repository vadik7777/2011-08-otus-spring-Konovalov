package ru.otus.homework12.service;

import ru.otus.homework12.dto.GenreDto;

import java.util.List;

public interface GenreService {

    List<GenreDto> findAll();
}