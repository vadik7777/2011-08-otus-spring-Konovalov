package ru.otus.project.rnis.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import ru.otus.project.rnis.dto.rnis.ObjectInfoDto;
import ru.otus.project.rnis.dto.rnis.TreeDto;
import ru.otus.project.rnis.feign.RnisSecureProxy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static ru.otus.project.rnis.constants.RnisConstants.*;

@DisplayName("Сервис для интеграции должен")
@SpringBootTest
class RnisServiceTest {

    @MockBean
    RnisSecureProxy rnisSecureProxy;

    @Autowired
    private RnisService rnisService;

    @DisplayName("проверить соединение")
    @Test
    void shouldCorrectPing() {
        when(rnisSecureProxy.ping()).thenReturn(ResponseEntity.ok(RNIS_PING_RESPONSE_SUCCESS_BODY));
        assertEquals(RNIS_PING_RESPONSE_SUCCESS_BODY, rnisService.ping().getBody());
    }

    @DisplayName("получать информацию об объекте")
    @Test
    void shouldCorrectGetObjectInfo() {
        var oid = ID_OBJECT_INFO_WITH_TEL_AND_SENSORS;
        var mockObjectInfoDto = new ObjectInfoDto();
        mockObjectInfoDto.setResult(RNIS_RESULT_SUCCESS_RESPONSE);

        when(rnisSecureProxy.fullObjInfo(oid)).thenReturn(mockObjectInfoDto);
        assertEquals(RNIS_RESULT_SUCCESS_RESPONSE, rnisService.getObjectInfo(oid).getResult());
    }

    @DisplayName("не получать информацию об объекте")
    @Test
    void shouldInCorrectGetObjectInfo() {
        var oid = ID_OBJECT_INFO_WITH_TEL_AND_SENSORS;
        var mockObjectInfoDto = new ObjectInfoDto();

        when(rnisSecureProxy.fullObjInfo(oid)).thenReturn(mockObjectInfoDto);
        assertEquals(ID_WRONG_OBJECT_INFO, rnisService.getObjectInfo(oid).getOid());
    }

    @DisplayName("получать информацию о дереве объектов")
    @Test
    void shouldCorrectGetTree() {
        var mockTreeDto = new TreeDto();
        mockTreeDto.setResult(RNIS_RESULT_SUCCESS_RESPONSE);
        when(rnisSecureProxy.getTree(true)).thenReturn(mockTreeDto);

        assertEquals(RNIS_RESULT_SUCCESS_RESPONSE, rnisService.getTree().getResult());
    }

    @DisplayName("не получать информацию о дереве объектов")
    @Test
    void shouldInCorrectGetTree() {
        var mockTreeDto = new TreeDto();
        when(rnisSecureProxy.getTree(true)).thenReturn(mockTreeDto);

        assertNull(rnisService.getTree().getResult());
    }
}