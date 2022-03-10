package ru.otus.project.rnis.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.otus.project.rnis.dto.rest.MunicipalityDto;
import ru.otus.project.rnis.entity.Municipality;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class MunicipalityMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    private void init() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(Municipality.class, MunicipalityDto.class);
        modelMapper.createTypeMap(MunicipalityDto.class, Municipality.class);
    }

    public MunicipalityDto toDto(Municipality municipality) {
        return modelMapper.map(municipality, MunicipalityDto.class);
    }

    public Municipality toEntity(MunicipalityDto municipalityDto) {
        return modelMapper.map(municipalityDto, Municipality.class);
    }
}