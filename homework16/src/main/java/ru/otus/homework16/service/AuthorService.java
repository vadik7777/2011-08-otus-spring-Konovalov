package ru.otus.homework16.service;

import ru.otus.homework16.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> findAll();
}