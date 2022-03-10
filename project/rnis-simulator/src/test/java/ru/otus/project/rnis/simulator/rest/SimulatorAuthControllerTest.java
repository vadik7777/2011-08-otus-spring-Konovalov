package ru.otus.project.rnis.simulator.rest;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import ru.otus.project.rnis.simulator.config.SimulatorSecureConfig;
import ru.otus.project.rnis.simulator.dto.model.AuthenticationModel;
import ru.otus.project.rnis.simulator.service.SimulatorAuthService;
import ru.otus.project.rnis.simulator.constants.SimulatorConstants;

import javax.servlet.http.Cookie;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для аутентификации должен")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnableConfigurationProperties(value = SimulatorSecureConfig.class)
@WebMvcTest(SimulatorAuthController.class)
class SimulatorAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SimulatorSecureConfig simulatorSecureConfig;

    @MockBean
    private SimulatorAuthService simulatorAuthService;

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

    @DisplayName("возвращать куку")
    @Test
    void shouldCorrectConnect() throws Exception {
        var login = simulatorSecureConfig.getLogin();
        var password = simulatorSecureConfig.getPassword();

        var params = new LinkedMultiValueMap<String, String>();
        params.add(AuthenticationModel.Fields.login, login);
        params.add(AuthenticationModel.Fields.password, password);

        when(simulatorAuthService.getSecureCookie(login, password)).thenReturn(Optional.of(successCookieValue));

        mockMvc.perform(get("/connect").queryParams(params))
               .andExpect(status().isOk())
               .andExpect(cookie().value(SimulatorConstants.SECURE_COOKIE, successCookieValue))
               .andExpect(content().string(SimulatorConstants.CONNECT_RESPONSE_SUCCESS_BODY));

        verify(simulatorAuthService, times(1)).getSecureCookie(login, password);
    }

    @DisplayName("не возвращать куку")
    @Test
    void shouldInCorrectConnect() throws Exception {

        when(simulatorAuthService.getSecureCookie(null, null)).thenReturn(Optional.empty());

        mockMvc.perform(get("/connect"))
               .andExpect(status().isOk())
               .andExpect(content().string(SimulatorConstants.CONNECT_RESPONSE_FAILURE_BODY));

        verify(simulatorAuthService, times(1)).getSecureCookie(null, null);
    }

    @DisplayName("успешно делать пинг сервера")
    @Test
    void shouldCorrectPing() throws Exception {
        when(simulatorAuthService.checkSecureCookieValue(successCookieValue)).thenReturn(true);

        mockMvc.perform(get("/ping").cookie(successCookie))
               .andExpect(status().isOk())
               .andExpect(content().string(SimulatorConstants.PING_RESPONSE_SUCCESS_BODY));

        verify(simulatorAuthService, times(1)).checkSecureCookieValue(successCookieValue);
    }

    @DisplayName("ошибочно делать пинг сервера")
    @Test
    void shouldInCorrectPing() throws Exception {
        when(simulatorAuthService.checkSecureCookieValue(failureCookieValue)).thenReturn(false);

        mockMvc.perform(get("/ping").cookie(failureCookie))
               .andExpect(status().isOk())
               .andExpect(content().string(SimulatorConstants.PING_RESPONSE_FAILURE_BODY));

        verify(simulatorAuthService, times(1)).checkSecureCookieValue(failureCookieValue);
    }

}