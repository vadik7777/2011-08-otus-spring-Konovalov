package ru.otus.project.rnis.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import ru.otus.project.rnis.dto.rest.MunicipalityDto;
import ru.otus.project.rnis.dto.rest.OrganizationDto;
import ru.otus.project.rnis.dto.rest.TransportTypeDto;
import ru.otus.project.rnis.dto.rest.TransportUnitDto;
import ru.otus.project.rnis.dto.rnis.TreeDto;

import java.io.IOException;
import java.time.Instant;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Конвертер для работы с деревом объектов должен")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class TreeConverterTest {

    @Autowired
    private TreeConverter treeConverter;

    @Autowired
    ObjectMapper objectMapper;

    private final static int TRANSPORT_TYPE_COUNT = 6;
    private final static int MUNICIPALITIES_COUNT = 118;
    private final static int ORGANIZATION_COUNT = 646;
    private final static int TRANSPORT_UNIT_COUNT = 4245;

    private final static Long ID_TRANSPORT_TYPE = 3L;
    private final static Long ID_MUNICIPALITY = 5L;
    private final static Long ID_ORGANIZATION = 6L;
    private final static Long ID_TRANSPORT_UNIT = 1L;

    private final static TransportTypeDto TRANSPORT_TYPE_DTO = new TransportTypeDto(ID_TRANSPORT_TYPE,
                                                                                    "Транспорный тип 3");
    private final static MunicipalityDto MUNICIPALITY_DTO = new MunicipalityDto(ID_MUNICIPALITY,
                                                                                "Муниципальное образование 5");
    private final static OrganizationDto ORGANIZATION_DTO = new OrganizationDto(ID_ORGANIZATION, "Организация 6");
    private final static TransportUnitDto TRANSPORT_UNIT_DTO = initTUDto();

    @Value("${rnis-service.tree-json}")
    private Resource treeJson;

    private static TransportUnitDto initTUDto() {
        return new TransportUnitDto(ID_TRANSPORT_UNIT,
                                    "Транспортная единица 1",
                                    null,
                                    Instant.parse("2022-02-14T03:44:26Z"),
                                    44.51461,
                                    48.75955,
                                    null,
                                    207.0,
                                    MUNICIPALITY_DTO,
                                    TRANSPORT_TYPE_DTO,
                                    ORGANIZATION_DTO);
    }

    private TreeDto treeDto;

    @BeforeAll
    void init() throws IOException {
        treeDto = objectMapper.readValue(treeJson.getInputStream(), TreeDto.class);;
    }

    @DisplayName("получать список транспортных типов")
    @Test
    void shouldCorrectGetTransportTypes() {
        var transportTypeDtos = treeConverter.getTransportTypes(treeDto);
        var actualTransportTypeDto = transportTypeDtos.stream()
                                                      .filter(type -> Objects.equals(ID_TRANSPORT_TYPE, type.getId()))
                                                      .findFirst().orElseThrow();
        assertAll(
                () -> assertEquals(TRANSPORT_TYPE_COUNT, transportTypeDtos.size()),
                () -> assertEquals(TRANSPORT_TYPE_DTO, actualTransportTypeDto)
        );
    }

    @DisplayName("получать список МО")
    @Test
    void shouldCorrectGetMunicipalities() {
        var municipalityDtos = treeConverter.getMunicipalities(treeDto);
        var actualMunicipalityDto = municipalityDtos.stream()
                                                    .filter(type -> Objects.equals(ID_MUNICIPALITY, type.getId()))
                                                    .findFirst().orElseThrow();
        assertAll(
                () -> assertEquals(MUNICIPALITIES_COUNT, municipalityDtos.size()),
                () -> assertEquals(MUNICIPALITY_DTO, actualMunicipalityDto)
        );
    }

    @DisplayName("получать список организаций")
    @Test
    void shouldCorrectGetOrganizations() {
        var organizationDtos = treeConverter.getOrganizations(treeDto);
        var actualOrganizationDto = organizationDtos.stream()
                                                    .filter(type -> Objects.equals(ID_ORGANIZATION, type.getId()))
                                                    .findFirst().orElseThrow();
        assertAll(
                () -> assertEquals(ORGANIZATION_COUNT, organizationDtos.size()),
                () -> assertEquals(ORGANIZATION_DTO, actualOrganizationDto)
        );
    }

    @DisplayName("получать список транспортных единиц")
    @Test
    void shouldCorrectGetTransportUnits() {
        var transportUnitDtos = treeConverter.getTransportUnits(treeDto);
        var actualTransportUnitDto = transportUnitDtos.stream()
                                                      .filter(type -> Objects.equals(ID_TRANSPORT_UNIT, type.getId()))
                                                      .findFirst().orElseThrow();
        assertAll(
                () -> assertEquals(TRANSPORT_UNIT_COUNT, transportUnitDtos.size()),
                () -> assertEquals(TRANSPORT_UNIT_DTO, actualTransportUnitDto)
        );
    }
}