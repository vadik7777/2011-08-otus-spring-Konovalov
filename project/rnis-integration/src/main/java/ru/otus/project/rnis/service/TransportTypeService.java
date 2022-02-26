package ru.otus.project.rnis.service;

import ru.otus.project.rnis.dto.rest.TransportTypeDto;

import java.util.List;

public interface TransportTypeService {

    List<Long> saveAllAndReturnIds(List<TransportTypeDto> transportTypeDtoList);

    List<Long> findAllIds();

    boolean deleteAllByIdInBatch(List<Long> ids);

    List<TransportTypeDto> findAll();

}