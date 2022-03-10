package ru.otus.project.rnis.simulator.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.rnis.simulator.dto.model.AuthenticationModel;
import ru.otus.project.rnis.simulator.service.SimulatorAuthService;
import ru.otus.project.rnis.simulator.constants.SimulatorConstants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class SimulatorAuthController {

    private final SimulatorAuthService simulatorAuthService;

    @GetMapping("/connect")
    public ResponseEntity<String> connect(@ModelAttribute AuthenticationModel authenticationModel,
                                          HttpServletResponse response) {
        var login = authenticationModel.getLogin();
        var password = authenticationModel.getPassword();
        var optionalCookie = simulatorAuthService.getSecureCookie(login, password);

        if (optionalCookie.isPresent()) {
            response.addCookie(new Cookie(SimulatorConstants.SECURE_COOKIE, optionalCookie.get()));
            return ResponseEntity.ok().body(SimulatorConstants.CONNECT_RESPONSE_SUCCESS_BODY);
        }
        return ResponseEntity.ok(SimulatorConstants.CONNECT_RESPONSE_FAILURE_BODY);
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping(@CookieValue(value = SimulatorConstants.SECURE_COOKIE, defaultValue = "") String cookieValue) {
        if (simulatorAuthService.checkSecureCookieValue(cookieValue)) {
            return ResponseEntity.ok().body(SimulatorConstants.PING_RESPONSE_SUCCESS_BODY);
        }
        return ResponseEntity.ok().body(SimulatorConstants.PING_RESPONSE_FAILURE_BODY);
    }
}