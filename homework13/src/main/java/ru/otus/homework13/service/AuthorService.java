package ru.otus.homework13.service;

import ru.otus.homework13.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> findAll();
}