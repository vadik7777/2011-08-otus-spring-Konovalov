package ru.otus.project.rnis.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import ru.otus.project.rnis.dto.rest.MunicipalityDto;
import ru.otus.project.rnis.dto.rest.OrganizationDto;
import ru.otus.project.rnis.dto.rest.TransportTypeDto;
import ru.otus.project.rnis.dto.rest.TransportUnitDto;
import ru.otus.project.rnis.entity.Municipality;
import ru.otus.project.rnis.entity.Organization;
import ru.otus.project.rnis.entity.TransportType;
import ru.otus.project.rnis.entity.TransportUnit;
import ru.otus.project.rnis.repository.TransportUnitRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы с траспортными единицами должен")
@SpringBootTest
class TransportUnitServiceTest {

    @MockBean
    private TransportUnitRepository transportUnitRepository;

    @Autowired
    private TransportUnitService transportUnitService;

    private final static Long ID_1 = 1L;
    private final static Long ID_2 = 2L;

    private final static TransportUnit TU_1 = initTU1();
    private final static TransportUnit TU_1_FOR_UPDATE = initTU1ForUpdate();
    private final static TransportUnitDto TU_DTO_1 = initTUDto1();
    private final static TransportUnitDto TU_DTO_1_FOR_UPDATE = initTUDto1ForUpdate();
    private final static TransportUnitDto TU_SIMPLE_DTO_1 = initSimpleTUDto1();

    private final static TransportUnit TU_2 = initTU2();
    private final static TransportUnitDto TU_DTO_2 = initTUDto2();
    private final static TransportUnitDto TU_SIMPLE_DTO_2 = initSimpleTUDto2();

    private static TransportUnit initTU1() {
        return new TransportUnit(ID_1,
                                 "объект1",
                                 "телефон1",
                                 Instant.parse("2022-02-02T00:56:03Z"),
                                 45.1234,
                                 45.1234,
                                 "100 км/ч",
                                 100.0,
                                 new Municipality(ID_1, "МО1"),
                                 new TransportType(ID_1, "Тип транспорта1"),
                                 new Organization(ID_1, "Организация1"));
    }

    private static TransportUnit initTU1ForUpdate() {
        return new TransportUnit(ID_1,
                                 "объект1",
                                 "телефон1",
                                 Instant.parse("2022-02-02T00:56:03Z"),
                                 45.1234,
                                 45.1234,
                                 "100 км/ч",
                                 100.0,
                                 new Municipality(ID_1, "МО1"),
                                 new TransportType(ID_1, "Тип транспорта1"),
                                 new Organization(ID_1, "Организация1"));
    }

    private static TransportUnit initTU2() {
        return new TransportUnit(ID_2,
                                 "объект2",
                                 "телефон2",
                                 Instant.parse("2022-02-02T00:51:03Z"),
                                 45.1234,
                                 45.1234,
                                 "100 км/ч",
                                 100.0,
                                 new Municipality(ID_2, "МО2"),
                                 new TransportType(ID_2, "Тип транспорта2"),
                                 new Organization(ID_2, "Организация2"));
    }

    private static TransportUnitDto initTUDto1() {
        return new TransportUnitDto(ID_1,
                                    "объект1",
                                    "телефон1",
                                    Instant.parse("2022-02-02T00:56:03Z"),
                                    45.1234,
                                    45.1234,
                                    "100 км/ч",
                                    100.0,
                                    new MunicipalityDto(ID_1, "МО1"),
                                    new TransportTypeDto(ID_1, "Тип транспорта1"),
                                    new OrganizationDto(ID_1, "Организация1"));
    }

    private static TransportUnitDto initTUDto1ForUpdate() {
        return new TransportUnitDto(ID_1,
                                    "объект1",
                                    "телефон1",
                                    Instant.parse("2022-02-02T00:56:04Z"),
                                    45.1234,
                                    45.1234,
                                    "100 км/ч",
                                    100.0,
                                    new MunicipalityDto(ID_1, "МО1"),
                                    new TransportTypeDto(ID_1, "Тип транспорта1"),
                                    new OrganizationDto(ID_1, "Организация1"));
    }

    private static TransportUnitDto initTUDto2() {
        return new TransportUnitDto(ID_2,
                                    "объект2",
                                    "телефон2",
                                    Instant.parse("2022-02-02T00:51:03Z"),
                                    45.1234,
                                    45.1234,
                                    "100 км/ч",
                                    100.0,
                                    new MunicipalityDto(ID_2, "МО2"),
                                    new TransportTypeDto(ID_2, "Тип транспорта2"),
                                    new OrganizationDto(ID_2, "Организация2"));
    }

    private static TransportUnitDto initSimpleTUDto1() {
        return new TransportUnitDto(ID_1,
                                    45.1234,
                                    45.1234,
                                    100.0,
                                    new TransportTypeDto(ID_1, "Тип транспорта1"));
    }

    private static TransportUnitDto initSimpleTUDto2() {
        return new TransportUnitDto(ID_2,
                                    45.1234,
                                    45.1234,
                                    100.0,
                                    new TransportTypeDto(ID_2, "Тип транспорта2"));
    }

    @DisplayName("сохранять список и возвращать их идентификаторы")
    @Test
    void shouldCorrectSaveAllAndReturnIds() {
        var transportUnitList = List.of(TU_1, TU_2);
        var transportUnitDtoList = List.of(TU_DTO_1, TU_DTO_2);

        when(transportUnitRepository.saveAll(transportUnitList)).thenReturn(transportUnitList);

        var expectedIds = List.of(ID_1, ID_2);
        var actualIds = transportUnitService.saveAllAndReturnIds(transportUnitDtoList);

        assertThat(actualIds).isEqualTo(expectedIds);
    }

    @DisplayName("обновлять транспортную единицу")
    @Test
    void shouldCorrectUpdate() {
        when(transportUnitRepository.findById(ID_1)).thenReturn(Optional.of(TU_1_FOR_UPDATE));

        var actualIsUpdate = transportUnitService.update(TU_DTO_1_FOR_UPDATE);

        assertTrue(actualIsUpdate);
        verify(transportUnitRepository, times(1)).findById(ID_1);
    }

    @DisplayName("не обновлять транспортную единицу")
    @Test
    void shouldInCorrectUpdate() {
        when(transportUnitRepository.findById(ID_1)).thenReturn(Optional.of(TU_1));

        var actualIsUpdate = transportUnitService.update(TU_DTO_1);

        assertFalse(actualIsUpdate);
        verify(transportUnitRepository, times(1)).findById(ID_1);
    }

    @DisplayName("возвращать список всех идентификаторов")
    @Test
    void shouldCorrectFindAllIds() {
        var expectedIds = List.of(ID_1, ID_2);

        when(transportUnitRepository.findAllIds()).thenReturn(expectedIds);

        var actualIds = transportUnitService.findAllIds();

        assertThat(actualIds).isEqualTo(expectedIds);
    }

    @DisplayName("удалять список по их идентификаторам")
    @Test
    void shouldCorrectDeleteAllByIdInBatch() {
        var ids = List.of(ID_1, ID_2);

        doNothing().when(transportUnitRepository).deleteAllByIdInBatch(ids);

        var actualIsDelete = transportUnitService.deleteAllByIdInBatch(ids);

        assertTrue(actualIsDelete);
        verify(transportUnitRepository, times(1)).deleteAllByIdInBatch(ids);
    }

    @DisplayName("получать список всех проекций")
    @Test
    void shouldCorrectFindAll() {
        var transportUnitList = List.of(TU_1, TU_2);
        var expectedTransportUnitDtoList = List.of(TU_SIMPLE_DTO_1, TU_SIMPLE_DTO_2);

        when(transportUnitRepository.findAll((Specification<TransportUnit>) null)).thenReturn(transportUnitList);

        var actualTransportUnitDtoList = transportUnitService.findAll(null);

        assertThat(actualTransportUnitDtoList).usingRecursiveComparison().isEqualTo(expectedTransportUnitDtoList);
    }

    @DisplayName("получать транспортную единицу по идентификатору")
    @Test
    void shouldCorrectFindById() {
        var transportUnit = Optional.of(TU_1);
        var expectedOptionalTransportUnitDto = Optional.of(TU_DTO_1);

        when(transportUnitRepository.findFullById(ID_1)).thenReturn(transportUnit);

        var actualOptionalTransportUnit = transportUnitService.findById(ID_1);

        assertThat(actualOptionalTransportUnit).usingRecursiveComparison().isEqualTo(expectedOptionalTransportUnitDto);
    }

}