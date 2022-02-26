package ru.otus.project.rnis.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.rnis.dto.rest.MunicipalityDto;
import ru.otus.project.rnis.entity.Municipality;
import ru.otus.project.rnis.mapper.MunicipalityMapper;
import ru.otus.project.rnis.repository.MunicipalityRepository;
import ru.otus.project.rnis.service.MunicipalityService;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MunicipalityServiceImpl implements MunicipalityService {

    private final MunicipalityRepository municipalityRepository;
    private final MunicipalityMapper municipalityMapper;
    private final MunicipalityServiceImpl self;

    public MunicipalityServiceImpl(MunicipalityRepository municipalityRepository,
                                   MunicipalityMapper municipalityMapper,
                                   @Lazy MunicipalityServiceImpl self) {
        this.municipalityRepository = municipalityRepository;
        this.municipalityMapper = municipalityMapper;
        this.self = self;
    }

    @HystrixCommand(groupKey = "municipality", commandKey = "save all municipalities and return ids", fallbackMethod = "saveAllAndReturnIdsFallback")
    @Override
    public List<Long> saveAllAndReturnIds(List<MunicipalityDto> municipalityDtoList) {
        return self.saveAllAndReturnIdsWithHystrix(municipalityDtoList);
    }

    @Transactional
    public List<Long> saveAllAndReturnIdsWithHystrix(List<MunicipalityDto> municipalityDtoList) {
        var municipalityList = municipalityDtoList.stream()
                                                  .map(municipalityMapper::toEntity)
                                                  .collect(Collectors.toList());
        municipalityList = municipalityRepository.saveAll(municipalityList);
        var ids = municipalityList.stream().map(Municipality::getId).collect(Collectors.toList());
        return ids;
    }

    public List<Long> saveAllAndReturnIdsFallback(List<MunicipalityDto> municipalityDtoList) {
        log.error("Failure save all municipalities and return ids, return default empty list");
        return List.of();
    }

    @HystrixCommand(groupKey = "municipality", commandKey = "find all municipalities ids", fallbackMethod = "findAllIdsFallback")
    @Override
    public List<Long> findAllIds() {
        return self.findAllIdsWithHystrix();
    }

    @Transactional(readOnly = true)
    public List<Long> findAllIdsWithHystrix() {
        return municipalityRepository.findAllIds();
    }

    public List<Long> findAllIdsFallback() {
        log.error("Failure find all municipalities ids,  return default empty list");
        return List.of();
    }

    @HystrixCommand(groupKey = "municipality", commandKey = "delete all municipalities by ids", fallbackMethod = "deleteAllByIdInBatchFallback")
    @Override
    public boolean deleteAllByIdInBatch(List<Long> ids) {
        return self.deleteAllByIdInBatchWithHystrix(ids);
    }

    @Transactional
    public boolean deleteAllByIdInBatchWithHystrix(List<Long> ids) {
        municipalityRepository.deleteAllByIdInBatch(ids);
        return true;
    }

    public boolean deleteAllByIdInBatchFallback(List<Long> ids) {
        log.error("Failure delete all municipalities by ids");
        return false;
    }

    @HystrixCommand(groupKey = "municipality", commandKey = "find all municipalities", fallbackMethod = "findAllFallback")
    @Override
    public List<MunicipalityDto> findAll() {
        return self.findAllWithHystrix();
    }

    @Transactional(readOnly = true)
    public List<MunicipalityDto> findAllWithHystrix() {
        return municipalityRepository.findAll().stream().map(municipalityMapper::toDto).collect(Collectors.toList());
    }

    public List<MunicipalityDto> findAllFallback() {
        log.error("Failure find all municipalities,  return default empty list");
        return List.of();
    }

}