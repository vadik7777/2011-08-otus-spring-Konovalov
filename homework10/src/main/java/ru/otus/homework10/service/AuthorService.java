package ru.otus.homework10.service;

import ru.otus.homework10.rest.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> findAll();
}