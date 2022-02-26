package ru.otus.project.rnis.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.project.rnis.dto.rest.MunicipalityDto;
import ru.otus.project.rnis.entity.Municipality;
import ru.otus.project.rnis.repository.MunicipalityRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы с МО должен")
@SpringBootTest
class MunicipalityServiceTest {

    @MockBean
    private MunicipalityRepository municipalityRepository;

    @Autowired
    private MunicipalityService municipalityService;

    private final static Long ID_1 = 1L;
    private final static Long ID_2 = 2L;
    private final static Long ID_3 = 3L;
    private final static Municipality MO_1 = new Municipality(ID_1, "МО1");
    private final static Municipality MO_2 = new Municipality(ID_2, "МО2");
    private final static Municipality MO_3 = new Municipality(ID_3, "МО3");
    private final static MunicipalityDto MO_DTO_1 = new MunicipalityDto(ID_1, "МО1");
    private final static MunicipalityDto MO_DTO_2 = new MunicipalityDto(ID_2, "МО2");
    private final static MunicipalityDto MO_DTO_3 = new MunicipalityDto(ID_3, "МО3");

    @DisplayName("сохранять список и возвращать их идентификаторы")
    @Test
    void shouldCorrectSaveAllAndReturnIds() {
        var municipalityList = List.of(MO_1, MO_2);

        var municipalityDtoList = List.of(MO_DTO_1, MO_DTO_2);

        when(municipalityRepository.saveAll(municipalityList)).thenReturn(municipalityList);

        var expectedIds = List.of(ID_1, ID_2);
        var actualIds = municipalityService.saveAllAndReturnIds(municipalityDtoList);

        assertThat(actualIds).isEqualTo(expectedIds);
    }

    @DisplayName("возвращать список всех идентификаторов")
    @Test
    void shouldCorrectFindAllIds() {
        var expectedIds = List.of(ID_1, ID_2);

        when(municipalityRepository.findAllIds()).thenReturn(expectedIds);

        var actualIds = municipalityService.findAllIds();

        assertThat(actualIds).isEqualTo(expectedIds);
    }

    @DisplayName("удалять список по их идентификаторам")
    @Test
    void shouldCorrectDeleteAllByIdInBatch() {
        var ids = List.of(ID_1, ID_2);

        doNothing().when(municipalityRepository).deleteAllByIdInBatch(ids);
        var actualIsDelete = municipalityService.deleteAllByIdInBatch(ids);

        assertTrue(actualIsDelete);
        verify(municipalityRepository, times(1)).deleteAllByIdInBatch(ids);
    }

    @DisplayName("возвращать список всех MO")
    @Test
    void shouldCorrectFindAll() {
        var municipalityList = List.of(MO_1, MO_2, MO_3);
        var expectedMunicipalityDtoList = List.of(MO_DTO_1, MO_DTO_2, MO_DTO_3);

        when(municipalityRepository.findAll()).thenReturn(municipalityList);

        var actualMunicipalityDtoList = municipalityService.findAll();

        assertThat(actualMunicipalityDtoList).isEqualTo(expectedMunicipalityDtoList);
    }
}