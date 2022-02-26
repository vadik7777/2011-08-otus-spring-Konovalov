package ru.otus.project.rnis.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.project.rnis.constants.RnisConstants;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Сервис для интеграции должен")
@SpringBootTest
class RnisServiceTest {

    @Autowired
    private RnisService rnisService;

    @DisplayName("проверить соединение")
    @Test
    void shouldCorrectPing() {
        assertEquals(RnisConstants.RNIS_PING_RESPONSE_SUCCESS_BODY, rnisService.ping().getBody());
    }

    @DisplayName("получить информацию об объекте")
    @Test
    void shouldCorrectGetObjectInfo() {
        var oid = RnisConstants.ID_OBJECT_INFO_WITH_TEL_AND_SENSORS;
        assertEquals(RnisConstants.RNIS_RESULT_SUCCESS_RESPONSE, rnisService.getObjectInfo(oid).getResult());
    }

    @DisplayName("получить информацию о дереве объектов")
    @Test
    void shouldCorrectGetTree() {
        assertEquals(RnisConstants.RNIS_RESULT_SUCCESS_RESPONSE, rnisService.getTree().getResult());
    }
}