package ru.otus.homework18.service;

import ru.otus.homework18.dto.GenreDto;

import java.util.List;

public interface GenreService {

    List<GenreDto> findAll();

    List<GenreDto> findAllFallbackMethod();
}