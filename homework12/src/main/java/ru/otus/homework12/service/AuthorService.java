package ru.otus.homework12.service;

import ru.otus.homework12.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> findAll();
}