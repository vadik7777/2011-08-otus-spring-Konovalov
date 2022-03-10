package ru.otus.project.rnis.converter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.project.rnis.converter.ObjectInfoConverter;
import ru.otus.project.rnis.dto.rest.NavigationInformationDto;
import ru.otus.project.rnis.dto.rest.TransportUnitDto;
import ru.otus.project.rnis.dto.rnis.ObjectInfoDto;
import ru.otus.project.rnis.mapper.ObjectInfoMapper;

@RequiredArgsConstructor
@Component
public class ObjectInfoConverterImpl implements ObjectInfoConverter {

    private final ObjectInfoMapper objectInfoMapper;

    @Override
    public TransportUnitDto getTransportUnit(ObjectInfoDto objectInfoDto) {
        return objectInfoMapper.toTransportUnitDto(objectInfoDto);
    }

    @Override
    public NavigationInformationDto getNavigationInformation(ObjectInfoDto objectInfoDto) {
        return objectInfoMapper.toNavigationInformationDto(objectInfoDto);
    }
}