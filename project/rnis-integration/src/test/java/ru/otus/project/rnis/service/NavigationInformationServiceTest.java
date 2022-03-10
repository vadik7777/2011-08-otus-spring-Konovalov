package ru.otus.project.rnis.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.otus.project.rnis.dto.rest.*;
import ru.otus.project.rnis.repository.NavigationInformationRepository;
import ru.otus.project.rnis.entity.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы с навигационной информацией должен")
@SpringBootTest
class NavigationInformationServiceTest {

    @MockBean
    private NavigationInformationRepository navigationInformationRepository;

    @Autowired
    private NavigationInformationService navigationInformationService;

    private final static Long ID_1 = 1L;
    private final static Long ID_2 = 2L;

    private final static TransportUnit TU_1 = initTU1();
    private final static TransportUnitDto TU_DTO_1 = initTU1Dto();

    private final static NavigationInformation NI_1 = initNI1();
    private final static NavigationInformationDto NI_DTO_1 = initNI1Dto();

    private final static NavigationInformation NI_2 = initNI2();
    private final static NavigationInformationDto NI_DTO_2 = initNI2Dto();

    private final static PageRequest PAGE_REQUEST = PageRequest.of(0, 2, Sort.by("id").descending());
    private final static Long TOTAL = 2L;
    private final static String[] IGNORE_COMPARATION_FIELDS = {"transportUnit"};


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

    private static TransportUnitDto initTU1Dto() {
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

    private static NavigationInformation initNI1() {
        return new NavigationInformation(ID_1,
                                         Instant.parse("2022-02-02T00:50:03Z"),
                                         45.1234,
                                         45.1234,
                                         "100 км/ч",
                                         100.0,
                                         TU_1);
    }

    private static NavigationInformationDto initNI1Dto() {
        return new NavigationInformationDto(ID_1,
                                            Instant.parse("2022-02-02T00:50:03Z"),
                                            45.1234,
                                            45.1234,
                                            "100 км/ч",
                                            100.0,
                                            TU_DTO_1);
    }

    private static NavigationInformation initNI2() {
        return new NavigationInformation(ID_2,
                                         Instant.parse("2022-02-02T00:55:03Z"),
                                         45.1234,
                                         45.1234,
                                         "100 км/ч",
                                         100.0,
                                         TU_1);
    }

    private static NavigationInformationDto initNI2Dto() {
        return new NavigationInformationDto(ID_2,
                                            Instant.parse("2022-02-02T00:55:03Z"),
                                            45.1234,
                                            45.1234,
                                            "100 км/ч",
                                            100.0,
                                            TU_DTO_1);
    }

    @DisplayName("сохранять навигационную информацию")
    @Test
    void shouldCorrectSave() {
        when(navigationInformationRepository.findFirstByTransportUnitIdOrderByIdDesc(ID_1))
                .thenReturn(Optional.of(NI_1));
        when(navigationInformationRepository.save(NI_2)).thenReturn(NI_2);

        var actualIsSave = navigationInformationService.save(NI_DTO_2);

        assertTrue(actualIsSave);
        verify(navigationInformationRepository, times(1)).save(NI_2);
        verify(navigationInformationRepository,
               times(1)).findFirstByTransportUnitIdOrderByIdDesc(ID_1);
    }

    @DisplayName("не сохранять навигационную информацию")
    @Test
    void shouldIncorrectSave() {
        when(navigationInformationRepository.findFirstByTransportUnitIdOrderByIdDesc(ID_1))
                .thenReturn(Optional.of(NI_2));

        var actualIsSave = navigationInformationService.save(NI_DTO_2);

        assertFalse(actualIsSave);
        verify(navigationInformationRepository, times(0)).save(NI_2);
        verify(navigationInformationRepository, times(1)).findFirstByTransportUnitIdOrderByIdDesc(ID_1);
    }

    @DisplayName("удалять список навигационной информации по идентификаторам транспортных единиц")
    @Test
    void shouldCorrectDeleteAllByTransportUnitId() {
        var ids = List.of(ID_1);

        doNothing().when(navigationInformationRepository).deleteAllByTransportUnitIdIn(ids);

        var isActualDelete = navigationInformationService.deleteAllByTransportUnitIds(ids);

        assertTrue(isActualDelete);
        verify(navigationInformationRepository, times(1)).deleteAllByTransportUnitIdIn(ids);
    }

    @DisplayName("получать постраничный список навигационной информации по идентификатору транспортной единицы")
    @Test
    void shouldCorrectFindAllByTransportUnitId() {
        var expectedNavigationInformationDtoList = new PageImpl<>(List.of(NI_DTO_2, NI_DTO_1), PAGE_REQUEST, TOTAL);

        when(navigationInformationRepository.findByTransportUnitId(ID_1, PAGE_REQUEST))
                .thenReturn(new PageImpl<>(List.of(NI_2, NI_1), PAGE_REQUEST, TOTAL));

        var actualNavigationInformationDtoList =
                navigationInformationService.findAllByTransportUnitId(ID_1, PAGE_REQUEST);

        assertThat(actualNavigationInformationDtoList).usingRecursiveComparison()
                                                      .ignoringFields(IGNORE_COMPARATION_FIELDS)
                                                      .isEqualTo(expectedNavigationInformationDtoList);
    }

}