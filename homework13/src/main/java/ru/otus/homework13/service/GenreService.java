package ru.otus.homework13.service;

import ru.otus.homework13.dto.GenreDto;

import java.util.List;

public interface GenreService {

    List<GenreDto> findAll();
}