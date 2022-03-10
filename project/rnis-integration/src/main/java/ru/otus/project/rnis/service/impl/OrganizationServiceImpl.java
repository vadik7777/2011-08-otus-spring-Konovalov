package ru.otus.project.rnis.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.rnis.dto.rest.OrganizationDto;
import ru.otus.project.rnis.entity.Organization;
import ru.otus.project.rnis.mapper.OrganizationMapper;
import ru.otus.project.rnis.repository.OrganizationRepository;
import ru.otus.project.rnis.service.OrganizationService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final OrganizationServiceImpl self;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository,
                                   OrganizationMapper organizationMapper,
                                   @Lazy OrganizationServiceImpl self) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
        this.self = self;
    }

    @HystrixCommand(groupKey = "organization", commandKey = "save all organizations and return ids", fallbackMethod = "saveAllAndReturnIdsFallback")
    @Override
    public List<Long> saveAllAndReturnIds(List<OrganizationDto> organizationDtoList) {
        return self.saveAllAndReturnIdsWithHystrix(organizationDtoList);
    }

    @Transactional
    public List<Long> saveAllAndReturnIdsWithHystrix(List<OrganizationDto> organizationDtoList) {
        var organizationList = organizationDtoList.stream()
                                                  .map(organizationMapper::toEntity)
                                                  .collect(Collectors.toList());
        organizationList = organizationRepository.saveAll(organizationList);
        var ids = organizationList.stream().map(Organization::getId).collect(Collectors.toList());
        return ids;
    }

    public List<Long> saveAllAndReturnIdsFallback(List<OrganizationDto> organizationDtoList) {
        log.error("Failure save all organizations and return ids, return default empty list");
        return List.of();
    }

    @HystrixCommand(groupKey = "organization", commandKey = "find all organizations ids", fallbackMethod = "findAllIdsFallback")
    @Override
    public List<Long> findAllIds() {
        return self.findAllIdsWithHystrix();
    }

    @Transactional(readOnly = true)
    public List<Long> findAllIdsWithHystrix() {
        return organizationRepository.findAllIds();
    }

    public List<Long> findAllIdsFallback() {
        log.error("Failure find all organizations ids,  return default empty list");
        return List.of();
    }

    @HystrixCommand(groupKey = "organization", commandKey = "delete all organizations by ids", fallbackMethod = "deleteAllByIdInBatchFallback")
    @Override
    public boolean deleteAllByIdInBatch(List<Long> ids) {
        return self.deleteAllByIdInBatchWithHystrix(ids);
    }

    @Transactional
    public boolean deleteAllByIdInBatchWithHystrix(List<Long> ids) {
        organizationRepository.deleteAllByIdInBatch(ids);
        return true;
    }

    public boolean deleteAllByIdInBatchFallback(List<Long> ids) {
        log.error("Failure delete all organizations by ids");
        return false;
    }
}