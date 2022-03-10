package ru.otus.project.rnis.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.otus.project.rnis.dto.rest.OrganizationDto;
import ru.otus.project.rnis.entity.Organization;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class OrganizationMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    private void init() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(Organization.class, OrganizationDto.class);
        modelMapper.createTypeMap(OrganizationDto.class, Organization.class);
    }

    public OrganizationDto toDto(Organization organization) {
        return modelMapper.map(organization, OrganizationDto.class);
    }

    public Organization toEntity(OrganizationDto organizationDto) {
        return modelMapper.map(organizationDto, Organization.class);
    }
}