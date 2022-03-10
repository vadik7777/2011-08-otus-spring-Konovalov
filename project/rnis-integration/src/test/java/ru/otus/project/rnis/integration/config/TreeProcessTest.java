package ru.otus.project.rnis.integration.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.project.rnis.converter.TreeConverter;
import ru.otus.project.rnis.dto.rnis.TreeDto;
import ru.otus.project.rnis.integration.gateway.TreeProcessGateway;
import ru.otus.project.rnis.service.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@DisplayName("Процесс обновления дерева объектов должен")
@SpringBootTest
class TreeProcessTest {

    @Autowired
    TreeProcessGateway treeProcessGateway;

    @MockBean
    private RnisService rnisService;

    @MockBean
    private TreeConverter treeConverter;

    @MockBean
    private TransportTypeService transportTypeService;

    @MockBean
    private MunicipalityService municipalityService;

    @MockBean
    private OrganizationService organizationService;

    @MockBean
    private TransportUnitService transportUnitService;

    @MockBean
    private NavigationInformationService navigationInformationService;

    @DisplayName("выполнить сценарий при пустом дереве объектов")
    @Test
    void shouldInCorrectPath() {
        when(rnisService.getTree()).thenReturn(new TreeDto());

        treeProcessGateway.treeProcess();

        verify(rnisService, times(1)).getTree();
        verify(treeConverter, times(0)).getTransportTypes(any());
        verify(transportTypeService, times(0)).saveAllAndReturnIds(any());
        verify(transportTypeService, times(0)).findAllIds();
        verify(transportTypeService, times(0)).deleteAllByIdInBatch(any());
        verify(treeConverter, times(0)).getMunicipalities(any());
        verify(municipalityService, times(0)).saveAllAndReturnIds(any());
        verify(municipalityService, times(0)).findAllIds();
        verify(municipalityService, times(0)).deleteAllByIdInBatch(any());
        verify(treeConverter, times(0)).getOrganizations(any());
        verify(organizationService, times(0)).saveAllAndReturnIds(any());
        verify(organizationService, times(0)).findAllIds();
        verify(organizationService, times(0)).deleteAllByIdInBatch(any());
        verify(treeConverter, times(0)).getTransportUnits(any());
        verify(transportUnitService, times(0)).saveAllAndReturnIds(any());
        verify(transportUnitService, times(0)).findAllIds();
        verify(transportUnitService, times(0)).deleteAllByIdInBatch(any());
        verify(navigationInformationService, times(0)).deleteAllByTransportUnitIds(any());

    }

    @DisplayName("выполнить сценарий при не пустом дереве объектов")
    @Test
    void shouldCorrectPath() {
        var treeDto = new TreeDto();
        treeDto.setChildren(List.of());

        when(rnisService.getTree()).thenReturn(treeDto);
        when(treeConverter.getTransportTypes(any())).thenReturn(List.of());
        when(transportTypeService.saveAllAndReturnIds(any())).thenReturn(List.of());
        when(transportTypeService.findAllIds()).thenReturn(new ArrayList<>());
        when(transportTypeService.deleteAllByIdInBatch(any())).thenReturn(true);
        when(treeConverter.getMunicipalities(any())).thenReturn(List.of());
        when(municipalityService.saveAllAndReturnIds(any())).thenReturn(List.of());
        when(municipalityService.findAllIds()).thenReturn(new ArrayList<>());
        when(municipalityService.deleteAllByIdInBatch(any())).thenReturn(true);
        when(treeConverter.getOrganizations(any())).thenReturn(List.of());
        when(organizationService.saveAllAndReturnIds(any())).thenReturn(List.of());
        when(organizationService.findAllIds()).thenReturn(new ArrayList<>());
        when(organizationService.deleteAllByIdInBatch(any())).thenReturn(true);
        when(treeConverter.getTransportUnits(any())).thenReturn(List.of());
        when(transportUnitService.saveAllAndReturnIds(any())).thenReturn(List.of());
        when(transportUnitService.findAllIds()).thenReturn(new ArrayList<>());
        when(transportUnitService.deleteAllByIdInBatch(any())).thenReturn(true);
        when(navigationInformationService.deleteAllByTransportUnitIds(any())).thenReturn(true);

        treeProcessGateway.treeProcess();

        verify(rnisService, times(1)).getTree();
        verify(treeConverter, times(1)).getTransportTypes(any());
        verify(transportTypeService, times(1)).saveAllAndReturnIds(any());
        verify(transportTypeService, times(1)).findAllIds();
        verify(transportTypeService, times(1)).deleteAllByIdInBatch(any());
        verify(treeConverter, times(1)).getMunicipalities(any());
        verify(municipalityService, times(1)).saveAllAndReturnIds(any());
        verify(municipalityService, times(1)).findAllIds();
        verify(municipalityService, times(1)).deleteAllByIdInBatch(any());
        verify(treeConverter, times(1)).getOrganizations(any());
        verify(organizationService, times(1)).saveAllAndReturnIds(any());
        verify(organizationService, times(1)).findAllIds();
        verify(organizationService, times(1)).deleteAllByIdInBatch(any());
        verify(treeConverter, times(1)).getTransportUnits(any());
        verify(transportUnitService, times(1)).saveAllAndReturnIds(any());
        verify(transportUnitService, times(1)).findAllIds();
        verify(transportUnitService, times(1)).deleteAllByIdInBatch(any());
        verify(navigationInformationService, times(1)).deleteAllByTransportUnitIds(any());

    }
}