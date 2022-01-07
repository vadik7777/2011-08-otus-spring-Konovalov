package ru.otus.homework16.service;

import ru.otus.homework16.dto.GenreDto;

import java.util.List;

public interface GenreService {

    List<GenreDto> findAll();
}