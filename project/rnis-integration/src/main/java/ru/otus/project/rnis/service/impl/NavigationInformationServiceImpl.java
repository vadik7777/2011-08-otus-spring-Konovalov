package ru.otus.project.rnis.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.rnis.dto.rest.NavigationInformationDto;
import ru.otus.project.rnis.mapper.NavigationInformationMapper;
import ru.otus.project.rnis.repository.NavigationInformationRepository;
import ru.otus.project.rnis.service.NavigationInformationService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NavigationInformationServiceImpl implements NavigationInformationService {

    private final NavigationInformationRepository navigationInformationRepository;
    private final NavigationInformationMapper navigationInformationMapper;
    private final NavigationInformationServiceImpl self;

    public NavigationInformationServiceImpl(NavigationInformationRepository navigationInformationRepository,
                                            NavigationInformationMapper navigationInformationMapper,
                                            @Lazy NavigationInformationServiceImpl self) {
        this.navigationInformationRepository = navigationInformationRepository;
        this.navigationInformationMapper = navigationInformationMapper;
        this.self = self;
    }

    @HystrixCommand(groupKey = "navigation information", commandKey = "save navigation information", fallbackMethod = "saveFallback")
    @Override
    public boolean save(NavigationInformationDto navigationInformationDto) {
        return self.saveWithHystrix(navigationInformationDto);
    }

    @Transactional
    public boolean saveWithHystrix(NavigationInformationDto navigationInformationDto) {
        var transportUnitId = navigationInformationDto.getTransportUnit().getId();
        var optionalNiFromDB =
                navigationInformationRepository.findFirstByTransportUnitIdOrderByIdDesc(transportUnitId);
        if (optionalNiFromDB.isPresent()) {
            var niFromDB = optionalNiFromDB.get();
            var currentInformationDate = Optional.ofNullable(navigationInformationDto.getInformationDate());
            var informationDate = Optional.ofNullable(niFromDB.getInformationDate());
            if (informationDate.equals(currentInformationDate)) {
                return false;
            }
        }
        navigationInformationRepository.save(navigationInformationMapper.toEntity(navigationInformationDto));
        return true;
    }

    public boolean saveFallback(NavigationInformationDto navigationInformationDto) {
        log.error("Failure save navigation information with transport unit id = {},  return default value = false",
                  navigationInformationDto.getTransportUnit().getId());
        return false;
    }

    @HystrixCommand(groupKey = "navigation information", commandKey = "delete all navigation information by transport units ids",
            fallbackMethod = "deleteAllByTransportUnitIdsFallback")
    @Override
    public boolean deleteAllByTransportUnitIds(List<Long> ids) {
        return self.deleteAllByTransportUnitIdsWithHystrix(ids);
    }

    @Transactional
    public boolean deleteAllByTransportUnitIdsWithHystrix(List<Long> ids) {
        navigationInformationRepository.deleteAllByTransportUnitIdIn(ids);
        return true;
    }

    public boolean deleteAllByTransportUnitIdsFallback(List<Long> ids) {
        log.error("Failure delete all navigation information by transport units ids");
        return false;
    }

    @HystrixCommand(groupKey = "navigation information", commandKey = "find all navigation information by transport unit id",
            fallbackMethod = "findAllByTransportUnitIdFallback")
    @Override
    public Page<NavigationInformationDto> findAllByTransportUnitId(Long id, Pageable pageable) {
        return self.findAllByTransportUnitIdWithHystrix(id, pageable);
    }

    @Transactional
    public Page<NavigationInformationDto> findAllByTransportUnitIdWithHystrix(Long id, Pageable pageable) {
        return navigationInformationRepository.findByTransportUnitId(id, pageable)
                                              .map(navigationInformationMapper::toDto);
    }

    public Page<NavigationInformationDto> findAllByTransportUnitIdFallback(Long id, Pageable pageable) {
        log.error("Failure find all navigation information by transport units id,  return default empty list");
        return new PageImpl<>(List.of());
    }

}