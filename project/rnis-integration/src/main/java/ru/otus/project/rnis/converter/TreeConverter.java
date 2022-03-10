package ru.otus.project.rnis.converter;

import ru.otus.project.rnis.dto.rest.MunicipalityDto;
import ru.otus.project.rnis.dto.rest.OrganizationDto;
import ru.otus.project.rnis.dto.rest.TransportTypeDto;
import ru.otus.project.rnis.dto.rest.TransportUnitDto;
import ru.otus.project.rnis.dto.rnis.TreeDto;

import java.util.List;

public interface TreeConverter {

    List<TransportTypeDto> getTransportTypes(TreeDto treeDto);

    List<MunicipalityDto> getMunicipalities(TreeDto treeDto);

    List<OrganizationDto> getOrganizations(TreeDto treeDto);

    List<TransportUnitDto> getTransportUnits(TreeDto treeDto);

}