package ru.otus.project.rnis.service;

import ru.otus.project.rnis.dto.rest.MunicipalityDto;

import java.util.List;

public interface MunicipalityService {

    List<Long> saveAllAndReturnIds(List<MunicipalityDto> municipalityDtoList);

    List<Long> findAllIds();

    boolean deleteAllByIdInBatch(List<Long> ids);

    List<MunicipalityDto> findAll();

}