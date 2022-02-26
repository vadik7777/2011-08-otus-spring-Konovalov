package ru.otus.project.rnis.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.rnis.dto.rest.TransportTypeDto;
import ru.otus.project.rnis.entity.TransportType;
import ru.otus.project.rnis.mapper.TransportTypeMapper;
import ru.otus.project.rnis.repository.TransportTypeRepository;
import ru.otus.project.rnis.service.TransportTypeService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransportTypeServiceImpl implements TransportTypeService {

    private final TransportTypeRepository transportTypeRepository;
    private final TransportTypeMapper transportTypeMapper;
    private final TransportTypeServiceImpl self;

    public TransportTypeServiceImpl(TransportTypeRepository transportTypeRepository,
                                    TransportTypeMapper transportTypeMapper,
                                    @Lazy TransportTypeServiceImpl self) {
        this.transportTypeRepository = transportTypeRepository;
        this.transportTypeMapper = transportTypeMapper;
        this.self = self;
    }

    @HystrixCommand(groupKey = "transport type", commandKey = "save all transport types and return ids", fallbackMethod = "saveAllAndReturnIdsFallback")
    @Override
    public List<Long> saveAllAndReturnIds(List<TransportTypeDto> transportTypeDtoList) {
        return self.saveAllAndReturnIdsWithHystrix(transportTypeDtoList);
    }

    @Transactional
    public List<Long> saveAllAndReturnIdsWithHystrix(List<TransportTypeDto> transportTypeDtoList) {
        var transportTypeList = transportTypeDtoList.stream()
                                                    .map(transportTypeMapper::toEntity)
                                                    .collect(Collectors.toList());
        transportTypeList = transportTypeRepository.saveAll(transportTypeList);
        var ids = transportTypeList.stream().map(TransportType::getId).collect(Collectors.toList());
        return ids;
    }

    public List<Long> saveAllAndReturnIdsFallback(List<TransportTypeDto> transportTypeDtoList) {
        log.error("Failure save all transport types and return ids, return default empty list");
        return List.of();
    }

    @HystrixCommand(groupKey = "transport type", commandKey = "find all transport types ids", fallbackMethod = "findAllIdsFallback")
    @Override
    public List<Long> findAllIds() {
        return self.findAllIdsWithHystrix();
    }

    @Transactional(readOnly = true)
    public List<Long> findAllIdsWithHystrix() {
        return transportTypeRepository.findAllIds();
    }

    public List<Long> findAllIdsFallback() {
        log.error("Failure find all transport types ids,  return default empty list");
        return List.of();
    }

    @HystrixCommand(groupKey = "transport type", commandKey = "delete all transport types by ids", fallbackMethod = "deleteAllByIdInBatchFallback")
    @Override
    public boolean deleteAllByIdInBatch(List<Long> ids) {
        return self.deleteAllByIdInBatchWithHystrix(ids);
    }

    @Transactional
    public boolean deleteAllByIdInBatchWithHystrix(List<Long> ids) {
        transportTypeRepository.deleteAllByIdInBatch(ids);
        return true;
    }

    public boolean deleteAllByIdInBatchFallback(List<Long> ids) {
        log.error("Failure delete all transport types by ids");
        return false;
    }

    @HystrixCommand(groupKey = "transport type", commandKey = "find all transport types", fallbackMethod = "findAllFallback")
    @Override
    public List<TransportTypeDto> findAll() {
        return self.findAllWithHystrix();
    }

    @Transactional(readOnly = true)
    public List<TransportTypeDto> findAllWithHystrix() {
        return transportTypeRepository.findAll().stream().map(transportTypeMapper::toDto).collect(Collectors.toList());
    }

    public List<TransportTypeDto> findAllFallback() {
        log.error("Failure find all transport types,  return default empty list");
        return List.of();
    }
}