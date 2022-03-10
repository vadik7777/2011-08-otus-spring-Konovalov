package ru.otus.project.rnis.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import ru.otus.project.rnis.feign.RnisAuthenticationProxy;
import ru.otus.project.rnis.model.CookieData;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.otus.project.rnis.constants.RnisConstants.*;

@DisplayName("Сервис аутентификации должен")
@SpringBootTest
class RnisAuthenticationServiceTest {

    @MockBean
    private RnisAuthenticationProxy rnisAuthenticationProxy;

    @MockBean
    private CookieData cookieData;

    @Autowired
    private RnisAuthenticationService rnisAuthenticationService;

    @DisplayName("проверить соединение и получить ошибку")
    @Test
    void shouldInCorrectCheckConnectionAndGetCookie() {
        when(rnisAuthenticationProxy.ping(any())).thenReturn(ResponseEntity.ok(""));
        when(rnisAuthenticationProxy.connect(any())).thenReturn(ResponseEntity.ok(""));

        assertThatThrownBy(() -> rnisAuthenticationService.checkConnectionAndGetCookie())
                .isInstanceOf(RuntimeException.class).hasMessage(RNIS_AUTHENTICATION_ERROR_MESSAGE);
    }

    @DisplayName("проверить соединение и получить куку")
    @Test
    void shouldCorrectCheckConnectionAndGetCookie() {
        when(rnisAuthenticationProxy.ping(any())).thenReturn(ResponseEntity.ok(""));
        when(cookieData.getCookie()).thenReturn("someCookie");
        var responseEntity = ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, RNIS_SECURE_COOKIE)
                                           .body(RNIS_CONNECT_RESPONSE_SUCCESS_BODY);
        when(rnisAuthenticationProxy.connect(any())).thenReturn(responseEntity);
        assertNotNull(rnisAuthenticationService.checkConnectionAndGetCookie());
    }

    @DisplayName("проверить соединение с установленной кукой")
    @Test
    void shouldCorrectCheckConnectionAndGetCookieWithOldCookie() {
        when(cookieData.getCookie()).thenReturn("someCookie");
        when(rnisAuthenticationProxy.ping(any())).thenReturn(ResponseEntity.ok(RNIS_PING_RESPONSE_SUCCESS_BODY));
        assertNotNull(rnisAuthenticationService.checkConnectionAndGetCookie());
    }
}