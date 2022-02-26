package ru.otus.project.rnis.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.otus.project.rnis.dto.rest.NavigationInformationDto;
import ru.otus.project.rnis.entity.NavigationInformation;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class NavigationInformationMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    private void init() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
                   .setPreferNestedProperties(false);
        modelMapper.createTypeMap(NavigationInformation.class, NavigationInformationDto.class)
                   .addMappings(mappings -> mappings.skip(NavigationInformationDto::setTransportUnit));
        modelMapper.createTypeMap(NavigationInformationDto.class, NavigationInformation.class);
    }

    public NavigationInformationDto toDto(NavigationInformation navigationInformation) {
        return modelMapper.map(navigationInformation, NavigationInformationDto.class);
    }

    public NavigationInformation toEntity(NavigationInformationDto navigationInformationDto) {
        return modelMapper.map(navigationInformationDto, NavigationInformation.class);
    }
}