package ru.otus.project.rnis.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.otus.project.rnis.dto.rest.TransportTypeDto;
import ru.otus.project.rnis.entity.TransportType;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class TransportTypeMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    private void init() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(TransportType.class, TransportTypeDto.class);
        modelMapper.createTypeMap(TransportTypeDto.class, TransportType.class);
    }

    public TransportTypeDto toDto(TransportType transportType) {
        return modelMapper.map(transportType, TransportTypeDto.class);
    }

    public TransportType toEntity(TransportTypeDto transportTypeDto) {
        return modelMapper.map(transportTypeDto, TransportType.class);
    }
}