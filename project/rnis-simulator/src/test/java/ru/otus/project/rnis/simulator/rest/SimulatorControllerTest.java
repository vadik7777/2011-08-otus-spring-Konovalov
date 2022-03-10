package ru.otus.project.rnis.simulator.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.project.rnis.simulator.config.SimulatorSecureConfig;
import ru.otus.project.rnis.simulator.dto.ObjectInfoDto;
import ru.otus.project.rnis.simulator.dto.TreeDto;
import ru.otus.project.rnis.simulator.service.SimulatorAuthService;
import ru.otus.project.rnis.simulator.service.SimulatorService;
import ru.otus.project.rnis.simulator.constants.SimulatorConstants;

import javax.servlet.http.Cookie;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для симулятора должен")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnableConfigurationProperties(value = SimulatorSecureConfig.class)
@WebMvcTest(SimulatorController.class)
class SimulatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SimulatorSecureConfig simulatorSecureConfig;

    @MockBean
    private SimulatorAuthService simulatorAuthService;

    @MockBean
    private SimulatorService simulatorService;

    private final static String TREE_PARAM = "all";
    private final static String TREE_PARAM_VALUE = "true";
    private final static String OBJECT_INFO_PARAM = "oid";
    private final static String OBJECT_INFO_PARAM_VALUE = "1";
    private final static TreeDto TREE_SUCCESS_DTO = new TreeDto(null, SimulatorConstants.RESULT_SUCCESS_RESPONSE);
    private final static TreeDto TREE_CLEAN_DTO = new TreeDto(null, SimulatorConstants.RESULT_SUCCESS_RESPONSE);
    private final static TreeDto TREE_FAILURE_DTO = new TreeDto(null, SimulatorConstants.RESULT_FAILURE_RESPONSE);
    private final static ObjectInfoDto OBJECT_INFO_SUCCESS_DTO = new ObjectInfoDto(SimulatorConstants.RESULT_SUCCESS_RESPONSE);
    private final static ObjectInfoDto OBJECT_INFO_WITH_TEL_AND_SPEED_DTO = new ObjectInfoDto(List.of(),
                                                                                              List.of(),
                                                                                              SimulatorConstants.RESULT_SUCCESS_RESPONSE);
    private final static ObjectInfoDto OBJECT_INFO_FAILURE_DTO = new ObjectInfoDto(SimulatorConstants.RESULT_FAILURE_RESPONSE);
    private final static ObjectInfoDto OBJECT_INFO_NO_PERMISSIONS_DTO = new ObjectInfoDto(SimulatorConstants.RESULT_NO_PERMISSION_RESPONSE);

    private String successCookieValue;
    private String failureCookieValue;

    private Cookie successCookie;
    private Cookie failureCookie;

    @BeforeAll
    void init() {
        successCookieValue = simulatorSecureConfig.getCookieValue();
        failureCookieValue = "";

        successCookie = new Cookie(SimulatorConstants.SECURE_COOKIE, successCookieValue);
        failureCookie = new Cookie(SimulatorConstants.SECURE_COOKIE, failureCookieValue);
    }

    @DisplayName("получить дерево объектов")
    @Test
    void shouldCorrectGetTreeSuccess() throws Exception {
        when(simulatorAuthService.checkSecureCookieValue(successCookieValue)).thenReturn(true);
        when(simulatorService.getTreeSuccess(true)).thenReturn(TREE_SUCCESS_DTO);
        var json = objectMapper.writeValueAsString(TREE_SUCCESS_DTO);

        mockMvc.perform(get("/gettree").cookie(successCookie).param(TREE_PARAM, TREE_PARAM_VALUE))
               .andExpect(status().isOk())
               .andExpect(content().json(json));

        verify(simulatorAuthService, times(1)).checkSecureCookieValue(successCookieValue);
        verify(simulatorService, times(1)).getTreeSuccess(true);
    }

    @DisplayName("получить пустое дерево объектов")
    @Test
    void shouldCorrectGetTreeCleanSuccess() throws Exception {
        when(simulatorAuthService.checkSecureCookieValue(successCookieValue)).thenReturn(true);
        when(simulatorService.getTreeSuccess(false)).thenReturn(TREE_CLEAN_DTO);
        var json = objectMapper.writeValueAsString(TREE_CLEAN_DTO);

        mockMvc.perform(get("/gettree").cookie(successCookie))
               .andExpect(status().isOk())
               .andExpect(content().json(json));

        verify(simulatorAuthService, times(1)).checkSecureCookieValue(successCookieValue);
        verify(simulatorService, times(1)).getTreeSuccess(false);
    }

    @DisplayName("получить ошибочное дерево объектов")
    @Test
    void shouldCorrectGetTreeFailure() throws Exception {
        when(simulatorAuthService.checkSecureCookieValue(failureCookieValue)).thenReturn(false);
        when(simulatorService.getTreeFailure()).thenReturn(TREE_FAILURE_DTO);
        var json = objectMapper.writeValueAsString(TREE_FAILURE_DTO);

        mockMvc.perform(get("/gettree").cookie(failureCookie).param(TREE_PARAM, TREE_PARAM_VALUE))
               .andExpect(status().isOk())
               .andExpect(content().json(json));

        verify(simulatorAuthService, times(1)).checkSecureCookieValue(failureCookieValue);
        verify(simulatorService, times(1)).getTreeFailure();
    }

    @DisplayName("получить информацию об объекте")
    @Test
    void shouldCorrectObjectInfoSuccess() throws Exception {
        when(simulatorAuthService.checkSecureCookieValue(successCookieValue)).thenReturn(true);
        when(simulatorService.getObjectInfoSuccess(any())).thenReturn(OBJECT_INFO_SUCCESS_DTO);
        var json = objectMapper.writeValueAsString(OBJECT_INFO_SUCCESS_DTO);

        mockMvc.perform(get("/fullobjinfo").cookie(successCookie).param(OBJECT_INFO_PARAM, OBJECT_INFO_PARAM_VALUE))
               .andExpect(status().isOk())
               .andExpect(content().json(json));

        verify(simulatorAuthService, times(1)).checkSecureCookieValue(successCookieValue);
        verify(simulatorService, times(1)).getObjectInfoSuccess(any());
    }

    @DisplayName("получить информацию об объекте с телефоном и скоростью")
    @Test
    void shouldCorrectObjectInfoSuccessWithTelAndSpeed() throws Exception {
        when(simulatorAuthService.checkSecureCookieValue(successCookieValue)).thenReturn(true);
        when(simulatorService.getObjectInfoSuccess(any())).thenReturn(OBJECT_INFO_WITH_TEL_AND_SPEED_DTO);
        var json = objectMapper.writeValueAsString(OBJECT_INFO_WITH_TEL_AND_SPEED_DTO);

        mockMvc.perform(get("/fullobjinfo").cookie(successCookie).param(OBJECT_INFO_PARAM, OBJECT_INFO_PARAM_VALUE))
               .andExpect(status().isOk())
               .andExpect(content().json(json));

        verify(simulatorAuthService, times(1)).checkSecureCookieValue(successCookieValue);
        verify(simulatorService, times(1)).getObjectInfoSuccess(any());
    }

    @DisplayName("получить информацию об объекте с несуществующим идентификатором")
    @Test
    void shouldCorrectObjectInfoNoPermission() throws Exception {
        when(simulatorAuthService.checkSecureCookieValue(successCookieValue)).thenReturn(true);
        when(simulatorService.getObjectInfoSuccess(any())).thenReturn(OBJECT_INFO_NO_PERMISSIONS_DTO);
        var json = objectMapper.writeValueAsString(OBJECT_INFO_NO_PERMISSIONS_DTO);

        mockMvc.perform(get("/fullobjinfo").cookie(successCookie).param(OBJECT_INFO_PARAM, OBJECT_INFO_PARAM_VALUE))
               .andExpect(status().isOk())
               .andExpect(content().json(json));

        verify(simulatorAuthService, times(1)).checkSecureCookieValue(successCookieValue);
        verify(simulatorService, times(1)).getObjectInfoSuccess(any());
    }

    @DisplayName("получить ошибочную информацию об объекте")
    @Test
    void shouldCorrectGetObjectInfoFailure() throws Exception {
        when(simulatorAuthService.checkSecureCookieValue(failureCookieValue)).thenReturn(false);
        when(simulatorService.getObjectInfoFailure()).thenReturn(OBJECT_INFO_FAILURE_DTO);
        var json = objectMapper.writeValueAsString(OBJECT_INFO_FAILURE_DTO);

        mockMvc.perform(get("/fullobjinfo").cookie(failureCookie).param(OBJECT_INFO_PARAM, OBJECT_INFO_PARAM_VALUE))
               .andExpect(status().isOk())
               .andExpect(content().json(json));

        verify(simulatorAuthService, times(1)).checkSecureCookieValue(failureCookieValue);
        verify(simulatorService, times(1)).getObjectInfoFailure();
    }
}