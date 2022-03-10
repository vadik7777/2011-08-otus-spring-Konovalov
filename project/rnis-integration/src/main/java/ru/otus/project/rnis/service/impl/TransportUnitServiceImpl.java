package ru.otus.project.rnis.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.rnis.dto.rest.TransportUnitDto;
import ru.otus.project.rnis.dto.rest.TransportUnitSimpleDto;
import ru.otus.project.rnis.entity.TransportUnit;
import ru.otus.project.rnis.mapper.TransportUnitMapper;
import ru.otus.project.rnis.repository.TransportUnitRepository;
import ru.otus.project.rnis.service.TransportUnitService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransportUnitServiceImpl implements TransportUnitService {

    private final static String[] IGNORE_ON_COPY_FIELDS = {"id", "municipality", "transportType", "organization"};

    private final TransportUnitRepository transportUnitRepository;
    private final TransportUnitMapper transportUnitMapper;
    private final TransportUnitServiceImpl self;

    public TransportUnitServiceImpl(TransportUnitRepository transportUnitRepository,
                                    TransportUnitMapper transportUnitMapper,
                                    @Lazy TransportUnitServiceImpl self) {
        this.transportUnitRepository = transportUnitRepository;
        this.transportUnitMapper = transportUnitMapper;
        this.self = self;
    }

    @HystrixCommand(groupKey = "transport unit", commandKey = "save all transport unit", fallbackMethod = "saveAllAndReturnIdsFallback")
    @Override
    public List<Long> saveAllAndReturnIds(List<TransportUnitDto> transportUnitDtoList) {
        return self.saveAllAndReturnIdsWithHystrix(transportUnitDtoList);
    }

    @Transactional
    public List<Long> saveAllAndReturnIdsWithHystrix(List<TransportUnitDto> transportUnitDtoList) {
        var transportUnitList = transportUnitDtoList.stream()
                                                    .map(transportUnitMapper::toEntity)
                                                    .collect(Collectors.toList());
        transportUnitList = transportUnitRepository.saveAll(transportUnitList);
        var ids = transportUnitList.stream().map(TransportUnit::getId).collect(Collectors.toList());
        return ids;
    }

    public List<Long> saveAllAndReturnIdsFallback(List<TransportUnitDto> transportUnitDtoList) {
        log.error("Failure save all transport units and return ids, return default empty list");
        return List.of();
    }

    @HystrixCommand(groupKey = "transport unit", commandKey = "update transport unit", fallbackMethod = "updateFallback",
            threadPoolKey = "objectInfoPool")
    @Override
    public boolean update(TransportUnitDto transportUnitDto) {
        return self.updateWithHystrix(transportUnitDto);
    }

    @Transactional
    public boolean updateWithHystrix(TransportUnitDto transportUnitDto) {
        var isUpdate= transportUnitRepository.findById(transportUnitDto.getId())
                                      .map(tu -> {
                                          var currentInformationDate = Optional.ofNullable(tu.getInformationDate());
                                          var informationDate = Optional.ofNullable(transportUnitDto.getInformationDate());
                                          if (!informationDate.equals(currentInformationDate)) {
                                              var updateTransportUnit = transportUnitMapper.toEntity(transportUnitDto);
                                              BeanUtils.copyProperties(updateTransportUnit, tu, IGNORE_ON_COPY_FIELDS);
                                              return true;
                                          }
                                          return false;
                                      }).orElseThrow();

        return isUpdate;
    }

    public boolean updateFallback(TransportUnitDto transportUnitDto) {
        log.error("Failure update transport unit with id = {},  return default value = false", transportUnitDto.getId());
        return false;
    }

    @HystrixCommand(groupKey = "transport unit", commandKey = "find all transport unit ids", fallbackMethod = "findAllIdsFallback")
    @Override
    public List<Long> findAllIds() {
        return self.findAllIdsWithHystrix();
    }

    @Transactional(readOnly = true)
    public List<Long> findAllIdsWithHystrix() {
        return transportUnitRepository.findAllIds();
    }

    public List<Long> findAllIdsFallback() {
        log.error("Failure find all transport units ids, return default empty list");
        return List.of();
    }

    @HystrixCommand(groupKey = "transport unit", commandKey = "delete all transport unit by ids", fallbackMethod = "deleteAllByIdInBatchFallback")
    @Override
    public boolean deleteAllByIdInBatch(List<Long> ids) {
        return self.deleteAllByIdInBatchWithHystrix(ids);
    }

    @Transactional
    public boolean deleteAllByIdInBatchWithHystrix(List<Long> ids) {
        transportUnitRepository.deleteAllByIdInBatch(ids);
        return true;
    }

    public boolean deleteAllByIdInBatchFallback(List<Long> ids) {
        log.error("Failure delete all transport unit by ids");
        return false;
    }

    @HystrixCommand(groupKey = "transport unit", commandKey = "find all transport units", fallbackMethod = "findAllFallback")
    @Override
    public List<TransportUnitSimpleDto> findAll(Specification<TransportUnit> specification) {
        return findAllWithHystrix(specification);
    }

    @Transactional(readOnly = true)
    public List<TransportUnitSimpleDto> findAllWithHystrix(Specification<TransportUnit> specification) {
        return transportUnitRepository.findAll(specification)
                                      .stream()
                                      .map(transportUnitMapper::toSimpleDto)
                                      .collect(Collectors.toList());
    }

    public List<TransportUnitSimpleDto> findAllFallback(Specification<TransportUnit> specification) {
        log.error("Failure find all transport units,  return default empty list");
        return List.of();
    }

    @HystrixCommand(groupKey = "transport unit", commandKey = "find transport unit by id", fallbackMethod = "findByIdFallback")
    @Override
    public Optional<TransportUnitDto> findById(Long id) {
        return findByIdWithHystrix(id);
    }

    @Transactional(readOnly = true)
    public Optional<TransportUnitDto> findByIdWithHystrix(Long id) {
        return transportUnitRepository.findFullById(id).map(transportUnitMapper::toDto);
    }

    public Optional<TransportUnitDto> findByIdFallback(Long id) {
        log.error("Failure find transport unit by id with id = {},  return new transport unit", id);
        return Optional.of(new TransportUnitDto());
    }

}