package ru.otus.homework10.service;

import ru.otus.homework10.rest.dto.GenreDto;

import java.util.List;

public interface GenreService {

    List<GenreDto> findAll();
}