package ru.otus.project.rnis.integration.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.project.rnis.converter.ObjectInfoConverter;
import ru.otus.project.rnis.dto.rest.NavigationInformationDto;
import ru.otus.project.rnis.dto.rest.TransportUnitDto;
import ru.otus.project.rnis.dto.rnis.ObjectInfoDto;
import ru.otus.project.rnis.integration.config.util.ProcessUtils;
import ru.otus.project.rnis.integration.gateway.ObjectInfoProcessGateway;
import ru.otus.project.rnis.service.NavigationInformationService;
import ru.otus.project.rnis.service.RnisService;
import ru.otus.project.rnis.service.TransportUnitService;

import java.util.List;

import static org.mockito.Mockito.*;

@DisplayName("Процесс обновления объектов должен")
@SpringBootTest(properties = "rnis-service.navigation-information-enable=true")
class ObjectInfoProcessWithNavigationInformationTest {

    private final static List<Long> ID_TRANSPORT_UNITS = List.of(1L, 2L);

    @Autowired
    private ObjectInfoProcessGateway objectInfoProcessGateway;

    @MockBean
    private RnisService rnisService;

    @MockBean
    private TransportUnitService transportUnitService;

    @MockBean
    private ObjectInfoConverter objectInfoConverter;

    @MockBean
    private ProcessUtils processUtils;

    @MockBean
    private NavigationInformationService navigationInformationService;

    @DisplayName("выполнить сценарий при пустом списоке идентификаторов транспортных единиц")
    @Test
    void shouldCorrectPathWithEmptyTransportUnitIds() {
        when(transportUnitService.findAllIds()).thenReturn(List.of());

        objectInfoProcessGateway.objectInfoProcess();

        verify(transportUnitService, times(1)).findAllIds();
        verify(rnisService, times(0)).getObjectInfo(any());
        verify(objectInfoConverter, times(0)).getTransportUnit(any());
        verify(objectInfoConverter, times(0)).getNavigationInformation(any());
        verify(transportUnitService, times(0)).update(any());
        verify(navigationInformationService, times(0)).save(any());
        verify(processUtils, times(1)).generateEndMessageForObjectInfoProcess();

    }

    @DisplayName("выполнить сценарий обновления транспортных единиц и навигационной информации")
    @Test
    void shouldCorrectPathUpdateTransportUnitAndSaveNavigationInformation() {

        when(transportUnitService.findAllIds()).thenReturn(ID_TRANSPORT_UNITS);
        when(rnisService.getObjectInfo(any())).thenReturn(new ObjectInfoDto());
        when(objectInfoConverter.getTransportUnit(any())).thenReturn(new TransportUnitDto());
        when(transportUnitService.update(any())).thenReturn(true);
        when(objectInfoConverter.getNavigationInformation(any())).thenReturn(new NavigationInformationDto());
        when(navigationInformationService.save(any())).thenReturn(true);
        when(processUtils.generateEndMessageForObjectInfoProcess()).thenReturn(true);

        objectInfoProcessGateway.objectInfoProcess();

        verify(transportUnitService, times(1)).findAllIds();
        verify(rnisService, times(2)).getObjectInfo(any());
        verify(objectInfoConverter, times(2)).getTransportUnit(any());
        verify(objectInfoConverter, times(2)).getNavigationInformation(any());
        verify(transportUnitService, times(2)).update(any());
        verify(navigationInformationService, times(2)).save(any());
        verify(processUtils, times(2)).generateEndMessageForObjectInfoProcess();

    }
}