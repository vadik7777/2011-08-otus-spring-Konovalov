package ru.otus.project.rnis.service;

import org.springframework.data.jpa.domain.Specification;
import ru.otus.project.rnis.dto.rest.TransportUnitDto;
import ru.otus.project.rnis.dto.rest.TransportUnitSimpleDto;
import ru.otus.project.rnis.entity.TransportUnit;

import java.util.List;
import java.util.Optional;

public interface TransportUnitService {

    List<Long> saveAllAndReturnIds(List<TransportUnitDto> transportUnitDtoList);

    boolean update(TransportUnitDto transportUnitDto);

    List<Long> findAllIds();

    boolean deleteAllByIdInBatch(List<Long> ids);

    List<TransportUnitSimpleDto> findAll(Specification<TransportUnit> specification);

    Optional<TransportUnitDto> findById(Long id);

}