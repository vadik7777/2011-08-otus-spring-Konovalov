package ru.otus.project.rnis.simulator.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.project.rnis.simulator.config.SimulatorSecureConfig;
import ru.otus.project.rnis.simulator.service.SimulatorAuthService;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SimulatorAuthServiceImpl implements SimulatorAuthService {

    private final SimulatorSecureConfig simulatorSecureConfig;

    @Override
    public Optional<String> getSecureCookie(String login, String password) {
        if (simulatorSecureConfig.getLogin().equals(login) && simulatorSecureConfig.getPassword().equals(password)) {
            var cookieValue = simulatorSecureConfig.getCookieValue();
            var optionalCookie = Optional.of(cookieValue);
            return optionalCookie;
        }
        return Optional.empty();
    }

    @Override
    public boolean checkSecureCookieValue(String checkedCookieValue) {
        return simulatorSecureConfig.getCookieValue().equals(checkedCookieValue);
    }
}