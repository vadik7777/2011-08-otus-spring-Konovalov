package ru.otus.project.rnis.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.project.rnis.dto.rest.NavigationInformationDto;

import java.util.List;

public interface NavigationInformationService {

    boolean deleteAllByTransportUnitIds(List<Long> ids);

    boolean save(NavigationInformationDto navigationInformationDto);

    Page<NavigationInformationDto> findAllByTransportUnitId(Long id, Pageable pageable);

}