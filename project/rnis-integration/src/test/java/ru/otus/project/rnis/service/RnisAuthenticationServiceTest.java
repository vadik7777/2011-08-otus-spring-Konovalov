package ru.otus.project.rnis.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.project.rnis.config.RnisAuthenticationConfig;
import ru.otus.project.rnis.constants.RnisConstants;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Сервис аутентификации должен")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest
class RnisAuthenticationServiceTest {

    @Autowired
    private RnisAuthenticationService rnisAuthenticationService;

    @Autowired
    private RnisAuthenticationConfig rnisAuthenticationConfig;

    private String login;

    @BeforeAll
    void init() {
        login = rnisAuthenticationConfig.getLogin();
    }

    @DisplayName("проверить соединение и получить ошибку")
    @Order(1)
    @Test
    void shouldInCorrectCheckConnectionAndGetCookie() {
        rnisAuthenticationConfig.setLogin("");
        assertThatThrownBy(() -> rnisAuthenticationService.checkConnectionAndGetCookie())
                .isInstanceOf(RuntimeException.class).hasMessage(RnisConstants.RNIS_AUTHENTICATION_ERROR_MESSAGE);
    }

    @DisplayName("проверить соединение и получить куку")
    @Order(2)
    @Test
    void shouldCorrectCheckConnectionAndGetCookie() {
        rnisAuthenticationConfig.setLogin(login);
        assertNotNull(rnisAuthenticationService.checkConnectionAndGetCookie());
    }

    @DisplayName("проверить соединение с установленной кукой")
    @Order(3)
    @Test
    void shouldCorrectCheckConnectionAndGetCookieWithOldCookie() {
        rnisAuthenticationConfig.setLogin("");
        assertNotNull(rnisAuthenticationService.checkConnectionAndGetCookie());
    }
}