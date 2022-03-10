package ru.otus.project.rnis.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.otus.project.rnis.dto.rest.TransportUnitDto;
import ru.otus.project.rnis.dto.rest.TransportUnitSimpleDto;
import ru.otus.project.rnis.entity.TransportUnit;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class TransportUnitMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    private void init() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(TransportUnit.class, TransportUnitDto.class);
        modelMapper.createTypeMap(TransportUnit.class, TransportUnitSimpleDto.class);
        modelMapper.createTypeMap(TransportUnitDto.class, TransportUnit.class);
    }

    public TransportUnitDto toDto(TransportUnit transportUnit) {
        return modelMapper.map(transportUnit, TransportUnitDto.class);
    }

    public TransportUnitSimpleDto toSimpleDto(TransportUnit transportUnit) {
        return modelMapper.map(transportUnit, TransportUnitSimpleDto.class);
    }

    public TransportUnit toEntity(TransportUnitDto transportUnitDto) {
        return modelMapper.map(transportUnitDto, TransportUnit.class);
    }
}