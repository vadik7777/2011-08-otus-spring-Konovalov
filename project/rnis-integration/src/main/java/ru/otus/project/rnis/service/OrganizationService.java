package ru.otus.project.rnis.service;

import ru.otus.project.rnis.dto.rest.OrganizationDto;

import java.util.List;

public interface OrganizationService {

    List<Long> saveAllAndReturnIds(List<OrganizationDto> organizationDtoList);

    List<Long> findAllIds();

    boolean deleteAllByIdInBatch(List<Long> ids);

}