package ru.otus.project.rnis.simulator.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.project.rnis.simulator.config.SimulatorSecureConfig;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Сервис для аутентификации должен")
@EnableConfigurationProperties(value = SimulatorSecureConfig.class)
@SpringBootTest
class SimulatorAuthServiceTest {

    @Autowired
    private SimulatorSecureConfig simulatorSecureConfig;

    @Autowired
    private SimulatorAuthService simulatorAuthService;

    @DisplayName("возвращать куку")
    @Test
    void shouldCorrectGetSecureCookie() {
        var expectedCookie = Optional.of(simulatorSecureConfig.getCookieValue());
        var login = simulatorSecureConfig.getLogin();
        var password = simulatorSecureConfig.getPassword();

        var actualCookie = simulatorAuthService.getSecureCookie(login, password);

        assertThat(actualCookie).isEqualTo(expectedCookie);
    }

    @DisplayName("не возвращать куку")
    @Test
    void shouldInCorrectGetSecureCookie() {
        var actualCookie = simulatorAuthService.getSecureCookie(null, null);
        assertThat(actualCookie).isEmpty();
    }

    @DisplayName("верно проверить куку")
    @Test
    void shouldCorrectCheckSecureCookieValue() {
        var actualCheck = simulatorAuthService.checkSecureCookieValue(simulatorSecureConfig.getCookieValue());
        assertTrue(actualCheck);
    }

    @DisplayName("ошибочно проверить куку")
    @Test
    void shouldInCorrectCheckSecureCookieValue() {
        var actualCheck = simulatorAuthService.checkSecureCookieValue(null);
        assertFalse(actualCheck);
    }

}