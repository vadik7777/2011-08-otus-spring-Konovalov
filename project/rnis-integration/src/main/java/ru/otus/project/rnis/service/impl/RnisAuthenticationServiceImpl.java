package ru.otus.project.rnis.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.otus.project.rnis.config.RnisAuthenticationConfig;
import ru.otus.project.rnis.dto.rnis.AuthenticationParamsDto;
import ru.otus.project.rnis.feign.RnisAuthenticationProxy;
import ru.otus.project.rnis.service.RnisAuthenticationService;

import static ru.otus.project.rnis.constants.RnisConstants.*;

@RequiredArgsConstructor
@Service
public class RnisAuthenticationServiceImpl implements RnisAuthenticationService {

    private final static String RNIS_SECURE_COOKIE_IN_RESPONSE = RNIS_SECURE_COOKIE + "=";

    private final RnisAuthenticationProxy rnisAuthenticationProxy;
    private final RnisAuthenticationConfig rnisAuthenticationConfig;
    private volatile String cookie;
    private volatile String cookieValue;

    @Override
    public String checkConnectionAndGetCookie() {
        var body = ping(cookieValue).getBody();
        if (RNIS_PING_RESPONSE_SUCCESS_BODY.equals(body)) {
            return cookie;
        }
        var response = connect();
        body = response.getBody();
        if (!RNIS_CONNECT_RESPONSE_SUCCESS_BODY.equals(body)) {
            throw new RuntimeException(RNIS_AUTHENTICATION_ERROR_MESSAGE);
        }
        var cookieList = response.getHeaders().get(HttpHeaders.SET_COOKIE);
        cookie = cookieList.stream()
                           .filter(cookie -> cookie.contains(RNIS_SECURE_COOKIE))
                           .findFirst()
                           .orElseThrow(() -> new RuntimeException(RNIS_AUTHENTICATION_ERROR_MESSAGE));
        cookieValue = cookie.replace(RNIS_SECURE_COOKIE_IN_RESPONSE, "");
        return cookie;
    }

    private ResponseEntity<String> connect() {

        var authenticationParamsDto = new AuthenticationParamsDto();
        authenticationParamsDto.setLogin(rnisAuthenticationConfig.getLogin());
        authenticationParamsDto.setPassword(rnisAuthenticationConfig.getPassword());
        authenticationParamsDto.setLang(rnisAuthenticationConfig.getLang());
        authenticationParamsDto.setTimezone(rnisAuthenticationConfig.getTimezone());

        return rnisAuthenticationProxy.connect(authenticationParamsDto);
    }

    private ResponseEntity<String> ping(String cookie) {
        return rnisAuthenticationProxy.ping(cookie);
    }
}