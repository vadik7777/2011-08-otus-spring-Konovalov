package ru.otus.project.rnis.converter;

import ru.otus.project.rnis.dto.rest.NavigationInformationDto;
import ru.otus.project.rnis.dto.rest.TransportUnitDto;
import ru.otus.project.rnis.dto.rnis.ObjectInfoDto;

public interface ObjectInfoConverter {

    TransportUnitDto getTransportUnit(ObjectInfoDto objectInfoDto);

    NavigationInformationDto getNavigationInformation(ObjectInfoDto objectInfoDto);

}