package ru.otus.project.rnis.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.project.rnis.dto.rest.TransportTypeDto;
import ru.otus.project.rnis.entity.TransportType;
import ru.otus.project.rnis.repository.TransportTypeRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы с траспортными типами должен")
@SpringBootTest
class TransportTypeServiceTest {

    @MockBean
    private TransportTypeRepository transportTypeRepository;

    @Autowired
    private TransportTypeService transportTypeService;

    private final static Long ID_1 = 1L;
    private final static Long ID_2 = 2L;
    private final static Long ID_3 = 3L;
    private final static TransportType TR_TYPE_1 = new TransportType(ID_1, "Тип транспорта1");
    private final static TransportType TR_TYPE_2 = new TransportType(ID_2, "Тип транспорта2");
    private final static TransportType TR_TYPE_3 = new TransportType(ID_3, "Тип транспорта3");
    private final static TransportTypeDto TR_TYPE_DTO_1 = new TransportTypeDto(ID_1, "Тип транспорта1");
    private final static TransportTypeDto TR_TYPE_DTO_2 = new TransportTypeDto(ID_2, "Тип транспорта2");
    private final static TransportTypeDto TR_TYPE_DTO_3 = new TransportTypeDto(ID_3, "Тип транспорта3");

    @DisplayName("сохранять список и возвращать их идентификаторы")
    @Test
    void shouldCorrectSaveAllAndReturnIds() {
        var transportTypeList = List.of(TR_TYPE_1, TR_TYPE_2);

        var transportTypeDtoList = List.of(TR_TYPE_DTO_1, TR_TYPE_DTO_2);

        when(transportTypeRepository.saveAll(transportTypeList)).thenReturn(transportTypeList);

        var expectedIds = List.of(ID_1, ID_2);
        var actualIds = transportTypeService.saveAllAndReturnIds(transportTypeDtoList);

        assertThat(actualIds).isEqualTo(expectedIds);
    }

    @DisplayName("возвращать список всех идентификаторов")
    @Test
    void shouldCorrectFindAllIds() {
        var expectedIds = List.of(ID_1, ID_2);

        when(transportTypeRepository.findAllIds()).thenReturn(expectedIds);

        var actualIds = transportTypeService.findAllIds();

        assertThat(actualIds).isEqualTo(expectedIds);
    }

    @DisplayName("удалять список по их идентификаторам")
    @Test
    void shouldCorrectDeleteAllByIdInBatch() {
        var ids = List.of(ID_1, ID_2);

        doNothing().when(transportTypeRepository).deleteAllByIdInBatch(ids);

        var actualIsDelete = transportTypeService.deleteAllByIdInBatch(ids);

        assertTrue(actualIsDelete);
        verify(transportTypeRepository, times(1)).deleteAllByIdInBatch(ids);
    }

    @DisplayName("возвращать список всех транспортных типов")
    @Test
    void shouldCorrectFindAll() {
        var transportTypeList = List.of(TR_TYPE_1, TR_TYPE_2, TR_TYPE_3);
        var expectedTransportTypeDtoList = List.of(TR_TYPE_DTO_1, TR_TYPE_DTO_2, TR_TYPE_DTO_3);

        when(transportTypeRepository.findAll()).thenReturn(transportTypeList);

        var actualTransportTypeDtoList = transportTypeService.findAll();

        assertThat(actualTransportTypeDtoList).isEqualTo(expectedTransportTypeDtoList);
    }
}