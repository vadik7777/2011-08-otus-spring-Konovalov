package ru.otus.homework18.service;

import ru.otus.homework18.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> findAll();

    List<AuthorDto> findAllFallbackMethod();
}