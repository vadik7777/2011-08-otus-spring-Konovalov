package ru.otus.project.rnis.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.project.rnis.dto.rest.OrganizationDto;
import ru.otus.project.rnis.entity.Organization;
import ru.otus.project.rnis.repository.OrganizationRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы с организациями должен")
@SpringBootTest
class OrganizationServiceTest {

    @MockBean
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationService organizationService;

    private final static Long ID_1 = 1L;
    private final static Long ID_2 = 2L;
    private final static Organization ORG_1 =  new Organization(ID_1, "Организация1");
    private final static Organization ORG_2 =  new Organization(ID_2, "Организация2");
    private final static OrganizationDto ORG_DTO_1 =  new OrganizationDto(ID_1, "Организация1");
    private final static OrganizationDto ORG_DTO_2 =  new OrganizationDto(ID_2, "Организация2");

    @DisplayName("сохранять список и возвращать их идентификаторы")
    @Test
    void shouldCorrectSaveAllAndReturnIds() {
        var organizationList = List.of(ORG_1, ORG_2);

        var organizationDtoList = List.of(ORG_DTO_1, ORG_DTO_2);

        when(organizationRepository.saveAll(organizationList)).thenReturn(organizationList);

        var expectedIds = List.of(ID_1, ID_2);
        var actualIds = organizationService.saveAllAndReturnIds(organizationDtoList);

        assertThat(actualIds).isEqualTo(expectedIds);
    }

    @DisplayName("возвращать список всех идентификаторов")
    @Test
    void shouldCorrectFindAllIds() {
        var expectedIds = List.of(ID_1, ID_2);

        when(organizationRepository.findAllIds()).thenReturn(expectedIds);

        var actualIds = organizationService.findAllIds();

        assertThat(actualIds).isEqualTo(expectedIds);
    }

    @DisplayName("удалять список по их идентификаторам")
    @Test
    void shouldCorrectDeleteAllByIdInBatch() {
        var ids = List.of(ID_1, ID_2);

        doNothing().when(organizationRepository).deleteAllByIdInBatch(ids);

        var actualIsDelete = organizationService.deleteAllByIdInBatch(ids);

        assertTrue(actualIsDelete);
        verify(organizationRepository, times(1)).deleteAllByIdInBatch(ids);
    }
}