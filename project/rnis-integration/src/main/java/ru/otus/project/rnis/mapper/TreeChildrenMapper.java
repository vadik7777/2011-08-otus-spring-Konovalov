package ru.otus.project.rnis.mapper;

import org.springframework.stereotype.Component;
import ru.otus.project.rnis.dto.rest.MunicipalityDto;
import ru.otus.project.rnis.dto.rest.OrganizationDto;
import ru.otus.project.rnis.dto.rest.TransportTypeDto;
import ru.otus.project.rnis.dto.rest.TransportUnitDto;
import ru.otus.project.rnis.dto.rnis.TreeChildrenDto;

@Component
public class TreeChildrenMapper {

    public TransportTypeDto toTransportTypeDto(TreeChildrenDto transportTypeTree) {
        return new TransportTypeDto(transportTypeTree.getRealId(), transportTypeTree.getName());
    }

    public MunicipalityDto toMunicipalityDto(TreeChildrenDto municipalityTreeDto) {
        return new MunicipalityDto(municipalityTreeDto.getRealId(), municipalityTreeDto.getName());
    }

    public OrganizationDto toOrganizationDto(TreeChildrenDto organizationTreeDto) {
        return new OrganizationDto(organizationTreeDto.getRealId(), organizationTreeDto.getName());
    }

    public TransportUnitDto toTransportUnitDto(TreeChildrenDto transportTypeTree, TreeChildrenDto municipalityTreeDto,
                                               TreeChildrenDto organizationTreeDto, TreeChildrenDto transportUnitTree) {
        var transportTypeDto = toTransportTypeDto(transportTypeTree);
        var municipalityDto = toMunicipalityDto(municipalityTreeDto);
        var organizationDto = toOrganizationDto(organizationTreeDto);

        var transportUnitDto = new TransportUnitDto();
        transportUnitDto.setId(transportUnitTree.getRealId());
        transportUnitDto.setObjectName(transportUnitTree.getName());
        transportUnitDto.setInformationDate(transportUnitTree.getDt());
        transportUnitDto.setLongitude(transportUnitTree.getLon());
        transportUnitDto.setLatitude(transportUnitTree.getLat());
        transportUnitDto.setDirection(transportUnitTree.getDirection());
        transportUnitDto.setTransportType(transportTypeDto);
        transportUnitDto.setMunicipality(municipalityDto);
        transportUnitDto.setOrganization(organizationDto);

        return transportUnitDto;
    }
}