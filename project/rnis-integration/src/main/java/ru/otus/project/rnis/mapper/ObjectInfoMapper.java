package ru.otus.project.rnis.mapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.otus.project.rnis.dto.rest.NavigationInformationDto;
import ru.otus.project.rnis.dto.rest.TransportUnitDto;
import ru.otus.project.rnis.dto.rnis.ObjectInfoDto;
import ru.otus.project.rnis.dto.rnis.PropertyDto;
import ru.otus.project.rnis.dto.rnis.SensorDto;
import ru.otus.project.rnis.constants.RnisConstants;

import java.util.List;
import java.util.Objects;

@Component
public class ObjectInfoMapper {

    public TransportUnitDto toTransportUnitDto(ObjectInfoDto objectInfoDto) {

        var properties = objectInfoDto.getProperties();
        var phoneOfResponsible = Objects.isNull(properties) ? null : findPersonPhone(properties);

        var sensors = objectInfoDto.getSensors();
        var speed = Objects.isNull(sensors) ? null : findSpeed(sensors);

        var transportUnitDto = new TransportUnitDto();
        transportUnitDto.setId(objectInfoDto.getOid());
        transportUnitDto.setObjectName(objectInfoDto.getName());
        transportUnitDto.setPhoneOfResponsible(phoneOfResponsible);
        transportUnitDto.setInformationDate(objectInfoDto.getDt());
        transportUnitDto.setLongitude(objectInfoDto.getLon());
        transportUnitDto.setLatitude(objectInfoDto.getLat());
        transportUnitDto.setSpeed(speed);
        transportUnitDto.setDirection(objectInfoDto.getDirection());

        return transportUnitDto;
    }

    public NavigationInformationDto toNavigationInformationDto(ObjectInfoDto objectInfoDto) {

        var sensors = objectInfoDto.getSensors();
        var speed = Objects.isNull(sensors) ? null : findSpeed(sensors);

        var navigationInformationDto = new NavigationInformationDto();
        navigationInformationDto.setInformationDate(objectInfoDto.getDt());
        navigationInformationDto.setLongitude(objectInfoDto.getLon());
        navigationInformationDto.setLatitude(objectInfoDto.getLat());
        navigationInformationDto.setSpeed(speed);
        navigationInformationDto.setDirection(objectInfoDto.getDirection());
        navigationInformationDto.setTransportUnit(new TransportUnitDto(objectInfoDto.getOid()));

        return navigationInformationDto;
    }

    private String findPersonPhone(List<PropertyDto> propertyDtoList) {
        return propertyDtoList.stream()
                              .filter(property -> Objects.equals(RnisConstants.PROPERTY_PHONE_LABEL, property.getName())
                                      && StringUtils.isNotEmpty(property.getVal()))
                              .findFirst()
                              .map(PropertyDto::getVal)
                              .orElse(null);
    }

    private String findSpeed(List<SensorDto> sensorDtoList) {
        return sensorDtoList.stream()
                            .filter(sensor -> Objects.equals(RnisConstants.SENSOR_SPEED_LABEL, sensor.getName())
                                    && StringUtils.isNotEmpty(sensor.getVal()))
                            .findFirst()
                            .map(SensorDto::getVal)
                            .orElse(null);
    }
}