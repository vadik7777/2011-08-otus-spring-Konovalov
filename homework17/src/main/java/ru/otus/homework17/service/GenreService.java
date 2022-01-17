package ru.otus.homework17.service;

import ru.otus.homework17.dto.GenreDto;

import java.util.List;

public interface GenreService {

    List<GenreDto> findAll();
}