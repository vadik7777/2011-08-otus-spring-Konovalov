package ru.otus.homework15.service;

import ru.otus.homework15.dto.MatryoshkaDto;

import java.util.List;

public interface MatryoshkaService {

    MatryoshkaDto create(MatryoshkaDto matryoshkaDto);

    List<MatryoshkaDto> findAll();
}
